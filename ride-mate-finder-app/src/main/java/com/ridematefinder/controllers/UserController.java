package com.ridematefinder.controllers;

import com.ridematefinder.repository.PictureRepository;
import com.ridematefinder.repository.UserRepository;
import com.ridematefinder.sql.Pictures;
import com.ridematefinder.sql.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    public UserController(UserRepository userRepository, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
    }

    @GetMapping("/profileForm")
    public String showProfileForm(Model model, HttpSession session ) {
        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> model.addAttribute("LoginUser", value));
        model.addAttribute("user", new User());
        return "profileForm";
    }

    @GetMapping("/showUserProfile")
    public String showProfile(Model model, HttpSession session){
        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> model.addAttribute("LoginUser", value));
        return "profile";
    }

    @PostMapping("/addUserData")
    public String addUserData(@Valid User user, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        System.out.println("test");
        if (result.hasErrors()) {
            return "profileForm";
        }
        try {
            System.out.println("test");
            // Save the picture data in the database
            Pictures picture = new Pictures();
            picture.setPictureData(file.getBytes());
            pictureRepository.save(picture);

            // Set the picture reference to the user
            UUID dirverId = UUID.randomUUID();
            user.setPictures(picture);
            user.setId(dirverId);
            userRepository.save(user);

            return "index";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error uploading picture. Please try again.");
            return "profileForm";
        }

    }

    @GetMapping("/userList")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userId");
        return "redirect:/";
    }

}
