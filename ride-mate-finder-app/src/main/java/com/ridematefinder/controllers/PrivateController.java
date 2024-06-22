package com.ridematefinder.controllers;

import com.ridematefinder.repository.UserRepository;
import com.ridematefinder.sql.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

import static java.util.UUID.randomUUID;

@Controller
public class PrivateController {
    private final UserRepository userRepository;

    public PrivateController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/messages")
    public String privateMessages(@AuthenticationPrincipal OAuth2User user, Model model, HttpSession session) {
        model.addAttribute("body", user.getAttribute("name"));
        try {
            String email = user.getAttribute("email").toString();
            Optional<User> userOptional = userRepository.getUserByEmail(email);
            if (userOptional.isEmpty()) {
                User newUser = new User();
                newUser.setAge(23);
                newUser.setName(user.getAttribute("name").toString());
                newUser.setEmail(email);
                newUser.setId(randomUUID());
                newUser.setDriverId(randomUUID());
                newUser.setIsDriver(0);
                newUser.setGender("Not Specified");
                newUser.setSurname("Not Specified");
                userRepository.save(newUser);
                session.setAttribute("userId", newUser.getId());
            }
            else {
                if (userOptional.get().getIsDriver() == 1){
                session.setAttribute("isDriver", 1);
                }
                session.setAttribute("userId", userOptional.get().getId());
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/routes/all";
    }
}
