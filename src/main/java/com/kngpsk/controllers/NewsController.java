package com.kngpsk.controllers;

import com.kngpsk.domain.News;
import com.kngpsk.domain.Paragraph;
import com.kngpsk.domain.User;
import com.kngpsk.services.CensorService;
import com.kngpsk.services.NewsService;
import com.kngpsk.services.ParagraphService;
import com.kngpsk.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class NewsController {

    @Autowired
    NewsService newsService;

    @Autowired
    ParagraphService paragraphService;

    @Autowired
    CensorService censorService;

    @GetMapping("/addNews")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String addNewsShow(){
        return "newsAdd";
    }

    @PostMapping("/addNews")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String newsSave(@AuthenticationPrincipal User user,
                           @Valid News news,
                           BindingResult bindingResult,
                           Model model,
                           @RequestParam("MainPic") MultipartFile pic,
                           @RequestParam(value = "ParText1", required = false,defaultValue = "") String text1,
                           @RequestParam("ParPic1") MultipartFile pic1,
                           @RequestParam(value = "ParText2",required = false,defaultValue = "") String text2,
                           @RequestParam("ParPic2") MultipartFile pic2){


        StringBuilder message = new StringBuilder();
        news.setAuthor(user);

        boolean headIsEmpty = StringUtils.isEmpty(news.getHead());
        if(headIsEmpty)model.addAttribute("headError","Head is Empty");

        boolean textIsEmpty = StringUtils.isEmpty(news.getText());
        if(headIsEmpty)model.addAttribute("textError","Text is Empty");

        //проверка на цензуру
        boolean censorErrors = false;
        String[] forCensor = {news.getHead(),news.getText(),text1,text2};
        String report = censorService.censor(forCensor);
        if(!StringUtils.isEmpty(report)){
            message.append(report);
            censorErrors = true;
        }

        if(headIsEmpty || textIsEmpty || censorErrors || bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.addAttribute("message",message.toString());

            model.addAttribute("news", news);

            model.mergeAttributes(errorsMap);

            return "newsAdd";

        }else {
            try {
                News addedNews = newsService.updateNews(news,news.getHead(),news.getText(),pic);
                paragraphService.addParagraph(addedNews, text1, 1, pic1);
                paragraphService.addParagraph(addedNews, text2, 2, pic2);
            } catch (IOException exception) {
                message.append("Error with load Pic!");
            }

            model.addAttribute("message", message.toString());

            return "redirect:/myAddNews";
        }
    }

    @GetMapping("/editNews/{news}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String editNewsShow(@PathVariable News news,
                               Model model){
        model.addAttribute("editNews",news);
        return "newsAdd";
    }

    @PostMapping("/editNews/{news}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String editNewsSave(@AuthenticationPrincipal User user,
                           @PathVariable News news,
                           Model model,
                           @RequestParam("head") String head,
                           @RequestParam("text") String text,
                           @RequestParam("MainPic") MultipartFile pic,
                           @RequestParam("ParText1") String text1,
                           @RequestParam("ParPic1") MultipartFile pic1,
                           @RequestParam("ParText2") String text2,
                           @RequestParam("ParPic2") MultipartFile pic2){


        boolean headIsEmpty = StringUtils.isEmpty(head);
        if(headIsEmpty)model.addAttribute("headError","Head is Empty");

        boolean textIsEmpty = StringUtils.isEmpty(text);
        if(textIsEmpty)model.addAttribute("textError","Text is Empty");

        if(headIsEmpty || textIsEmpty){
            model.addAttribute("editNews", news);
            return "newsAdd";
        }else {
            StringBuilder message = new StringBuilder();
            try {
                newsService.updateNews(news, head, text, pic);
                List<Paragraph> paragraphs = new ArrayList<>(news.getParagraphs());
                paragraphService.updateParagraph(paragraphs.get(0), text1, pic1);
                paragraphService.updateParagraph(paragraphs.get(1), text2, pic2);

            } catch (IOException exception) {
                message.append("Error with load Pic!");
            }

            model.addAttribute("message", message.toString());

            return "redirect:/myAddNews";
        }
    }

    @GetMapping ("/deleteNews/{news}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String deleteNews(@PathVariable News news){
        Set<Paragraph>paragraphs = news.getParagraphs();
        paragraphs.stream().forEach((p)->paragraphService.deleteParagraph(p));
        newsService.deleteNews(news);
        return "redirect:/myAddNews";
    }



    @GetMapping("/myAddNews")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String myAddedNews(@AuthenticationPrincipal User user,
                              Model model){

        model.addAttribute("allNews",newsService.findAllByAuthor(user,true));
        return "myNews";
    }

    @GetMapping("/allNews/{news}")
    public String showOneNews(@AuthenticationPrincipal User currentUser,
                              @PathVariable News news,
                              Model model){
        boolean isUsersNews = news.getAuthor().equals(currentUser);
        model.addAttribute("news",news);
        model.addAttribute("isUsersNews",isUsersNews);
        return "oneNews";
    }
}
