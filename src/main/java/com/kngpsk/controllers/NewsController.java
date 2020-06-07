package com.kngpsk.controllers;

import com.kngpsk.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {

    @Autowired
    NewsService newsService;

    @GetMapping("/addNews")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String userList(){
        return "newsAdd";
    }
}
