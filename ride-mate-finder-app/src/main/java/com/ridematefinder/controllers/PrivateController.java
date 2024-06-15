package com.ridematefinder.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class PrivateController {
    private final UserController userController;

    public PrivateController(UserController userController) {
        this.userController = userController;
    }

    @GetMapping("/messages")
    public String privateMessages(@AuthenticationPrincipal OAuth2User user, Model model) {
        model.addAttribute("body", user.getAttribute("name"));
        try {
            String email = user.getAttribute("email").toString();
            System.out.println(email);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return "response";

    }

}
