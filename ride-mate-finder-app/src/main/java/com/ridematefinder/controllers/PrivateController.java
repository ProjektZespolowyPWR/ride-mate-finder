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
                newUser.setAge(3);
                newUser.setName(user.getAttribute("name").toString());
                newUser.setEmail(email);
                newUser.setId(randomUUID());
                newUser.setDriverId(randomUUID());
                newUser.setIsDriver(0);
//                newUser.setDriverId(null);
                newUser.setGender("Not Specified");
                newUser.setSurname("Not Specified");
                userRepository.save(newUser);
                System.out.println("user id: "+newUser.getId());
                session.setAttribute("userId", newUser.getId());
            }
            else {
                System.out.println("nie istnieje");
                session.setAttribute("userId", userOptional.get().getId());
                System.out.println("user id: "+userOptional.get().getId());
                System.out.println("user age: "+userOptional.get().getAge());
                System.out.println("User name: "+userOptional.get().getName());
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("session id: " +session.getAttribute("userId"));
        return "response";

    }

}
