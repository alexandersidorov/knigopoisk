package com.kngpsk.controllers;


import com.kngpsk.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    NewsService newsService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("allNews",newsService.findAll(true));
        return "main";
    }

}