package com.kngpsk.controllers;

import com.kngpsk.domain.User;
import com.kngpsk.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProfileController {

    @Autowired
    UserService userService;

    //переход на вкладку profile
    @GetMapping("/profile/{user}")
    public String profileUser(@PathVariable User user,
                              @AuthenticationPrincipal User currentUser,
                              Model model){
        model.addAttribute("userChannel",user);
        model.addAttribute("isCurrentUser",user.equals(currentUser));

        return "profile";
    }

    //переход на страницу редактирования профиля пользователя
    @GetMapping("/profile/{user}/edit")
    public String profileUserEdit(@PathVariable User user,
                                  Model model){
        model.addAttribute("user",user);

        return "profileEdit";
    }

    //подтверждение редактирования профиля
    @PostMapping("/profile/{user}/edit")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("email") String email,
                                @RequestParam("avatar") MultipartFile avatar,
                                Model model){


        StringBuilder message = new StringBuilder();
        try{
            userService.updateProfile(user,username,password,email,avatar);
        }catch (IOException exception){
            message.append("Error with load avatar.");
        }
        model.addAttribute("message",message.toString());

        return "redirect:/profile/"+user.getId();
    }
}
