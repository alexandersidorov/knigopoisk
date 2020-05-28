package com.kngpsk.controllers;

import com.kngpsk.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    @GetMapping("/profile/{user}")
    public String profileUser(@PathVariable User user,
                              @AuthenticationPrincipal User currentUser,
                              Model model){
        model.addAttribute("userChannel",user);
        model.addAttribute("isCurrentUser",user.equals(currentUser));

        return "profile";

    }
}
