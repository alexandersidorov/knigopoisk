package com.kngpsk.controllers;

import com.kngpsk.domain.User;
import com.kngpsk.services.UserService;
import com.kngpsk.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;

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
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));

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
                                @Valid User saveUser,
                                BindingResult bindingResult,
                                Model model,
                                @RequestParam("MainPic") MultipartFile pic,
                                @RequestParam("password2") String passwordConfirm
                                ){

        boolean isPassErr = saveUser.getPassword() != null && !saveUser.getPassword().equals(passwordConfirm);
        if (isPassErr) {
            model.addAttribute("passwordError", "Passwords are different!");
        }

        Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if(errors.size()>0){
            //В случае редактирования профиля, допускается наличие незаполненного поля пароля
            if(errors.containsKey("passwordError"))errors.remove("passwordError");
        }

        if (isPassErr || errors.size()>0) {

            model.mergeAttributes(errors);

            model.addAttribute("user",saveUser);

            return "profileEdit";
        }

        StringBuilder message = new StringBuilder();
        try{
            userService.updateProfile(user,saveUser.getUsername(), saveUser.getPassword(), saveUser.getEmail(), pic);
        }catch (IOException exception){
            message.append("Error with load avatar.");
        }
        model.addAttribute("message",message.toString());

        return "redirect:/profile/"+user.getId();
    }

    @GetMapping("/subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.subscribe(currentUser, user);

        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("/unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.unsubscribe(currentUser, user);

        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("/{type}/{user}/list")
    public String userList(
            Model model,
            @PathVariable User user,
            @PathVariable String type
    ) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);

        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }

        return "subscriptions";
    }
}
