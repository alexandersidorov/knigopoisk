package com.kngpsk.controllers;

import com.kngpsk.domain.News;
import com.kngpsk.domain.User;
import com.kngpsk.services.NewsService;
import com.kngpsk.services.ParagraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class NewsController {

    @Autowired
    NewsService newsService;

    @Autowired
    ParagraphService paragraphService;

    @GetMapping("/addNews")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String addNewsShow(){
        return "newsAdd";
    }

    @PostMapping("/addNews")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String newsSave(@AuthenticationPrincipal User user,
                           @RequestParam("head") String head,
                           @RequestParam("text") String text,
                           @RequestParam("headPic") MultipartFile headPic,
                           @RequestParam("ParText1") String text1,
                           @RequestParam("ParPic1") MultipartFile pic1,
                           @RequestParam("ParText2") String text2,
                           @RequestParam("ParPic2") MultipartFile pic2,
                           Model model){


        StringBuilder message = new StringBuilder();
        try{
            News addedNews = newsService.addNews(user,head,text,headPic);
            paragraphService.addParagraph(addedNews,text1,pic1);
            paragraphService.addParagraph(addedNews,text2,pic2);
        }catch (IOException exception){
            message.append("Error with load Pic!");
        }

        model.addAttribute("message",message.toString());

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
    public String showOneNews(@PathVariable News news,
                              Model model){
        model.addAttribute("news",news);
        return "oneNews";
    }
}
