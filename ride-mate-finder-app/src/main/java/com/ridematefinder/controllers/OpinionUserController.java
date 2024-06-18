package com.ridematefinder.controllers;

import com.ridematefinder.repository.OpinionUserRepository;
import com.ridematefinder.repository.UserRepository;
import com.ridematefinder.sql.Opinionuser;
import com.ridematefinder.sql.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class OpinionUserController {

    private final OpinionUserRepository opinionUserRepository;
    private final UserRepository userRepository;

    public OpinionUserController(OpinionUserRepository opinionUserRepository, UserRepository userRepository) {
        this.opinionUserRepository = opinionUserRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/opinions")
    public String showOpinions(Model model, HttpSession session) {
        List<Opinionuser> opinions = opinionUserRepository.findAll();
        model.addAttribute("opinions", opinions);
        return "opinions";
    }

    @GetMapping("/addOpinionShowForm")
    public String showAddOpinionForm(Model model, HttpSession session) {
        model.addAttribute("opinionUser", new Opinionuser());
        List<User> users = userRepository.findAll();
        UUID currentUserId = (UUID) session.getAttribute("userId");
        if (currentUserId != null) {
            users.removeIf(user -> user.getId().equals(currentUserId));
        }
        model.addAttribute("users", users);
        return "addOpinion";
    }

    @PostMapping("/addOpinion")
    public String addOpinion(@ModelAttribute("opinionUser") Opinionuser opinionUser, BindingResult result, @RequestParam("getId") UUID getId, HttpSession session) {
        System.out.println("test add opinion");
        if (result.hasErrors()) {
            return "addOpinion";
        }
        opinionUser.setId(UUID.randomUUID());
        opinionUser.setUserByUserIdReceiver(userRepository.findById(getId).orElseThrow(() -> new RuntimeException("User not found")));
        UUID currentUserId = (UUID) session.getAttribute("userId");
        if (currentUserId != null) {
            opinionUser.setUserByUserIdSetter(userRepository.findById(currentUserId).orElseThrow(() -> new RuntimeException("User not found")));
        }
        opinionUserRepository.save(opinionUser);
        return "redirect:/opinions";
    }
}
