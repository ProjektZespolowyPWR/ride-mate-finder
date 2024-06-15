package com.ridematefinder.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @GetMapping("/public/messages")
    public String publicMessages(Model model, HttpSession session ) {
        model.addAttribute("body", "nobody");
        model.addAttribute("session", session.getAttribute("userId"));
        return "response";
    }
}
