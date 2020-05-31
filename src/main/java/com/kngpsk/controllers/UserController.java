package com.kngpsk.controllers;

import com.kngpsk.domain.Role;
import com.kngpsk.domain.User;
import com.kngpsk.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model){
        model.addAttribute("users",userService.findAll());
        model.addAttribute("isAdmin",true);
        return "userList";
    }

    @GetMapping("/user/{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userSave(@RequestParam String username,
                           @RequestParam Map<String,String> form,
                           @RequestParam("userId") User user){

        userService.saveUser(user,username,form);
        return "redirect:/user";
    }

    @GetMapping("/control-user")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String userListModer(Model model){
        model.addAttribute("users",userService.findUsers());
        model.addAttribute("isAdmin",false);
        return "userList";
    }

}
