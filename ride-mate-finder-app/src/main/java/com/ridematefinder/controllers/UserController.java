package com.ridematefinder.controllers;

import com.ridematefinder.repository.CarRepository;
import com.ridematefinder.repository.PictureRepository;
import com.ridematefinder.repository.UserRepository;
import com.ridematefinder.sql.Car;
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
import java.util.*;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final CarRepository carRepository;

    public UserController(UserRepository userRepository, PictureRepository pictureRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;
    }

    @GetMapping("/profileForm")
    public String showProfileForm(Model model, HttpSession session ) {
        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> { model.addAttribute("LoginUser", value);

            if (value.getPictures() != null) {
                System.out.println("test get picture");
                byte[] pictureData = value.getPictures().getPictureData();;
                String base64Image = Base64.getEncoder().encodeToString(pictureData);
                model.addAttribute("userImage", base64Image);
            }
        });
        model.addAttribute("user", user.orElse(new User()));
        System.out.println("show profile controller");
        System.out.println("user age"+user.get().getAge());

        return "profileForm";
    }

    @GetMapping("/showUserProfile")
    public String showProfile(Model model, HttpSession session){
        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> { model.addAttribute("LoginUser", value);
            if (value.getPictures() != null) {
                System.out.println("test get picture");
                byte[] pictureData = value.getPictures().getPictureData();;
                String base64Image = Base64.getEncoder().encodeToString(pictureData);
                model.addAttribute("userImage", base64Image);
            }
         });
        List<Car> cars = carRepository.findAllByUser(user.get());
        Map<UUID, String> carImages = new HashMap<>();
        cars.forEach(car -> {
            if (car.getPictures() != null) {
                byte[] pictureData = car.getPictures().getPictureData();
                String base64Image = Base64.getEncoder().encodeToString(pictureData);
                carImages.put(car.getId(), base64Image);
            }
        });
        model.addAttribute("carImages", carImages);
        model.addAttribute("cars", cars);
        return "profile";
    }

    @PostMapping("/updateUserData")
    public String addUserData(@Valid User user, BindingResult result, @RequestParam("file") MultipartFile file, Model model, HttpSession session) {
        System.out.println("test");
        if (result.hasErrors()) {
            return "profileForm";
        }
        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> existingUserOptional = userRepository.findById(userId);

        if (existingUserOptional.isEmpty()) {
            model.addAttribute("errorMessage", "User not found.");
            return "profileForm";
        }
        System.out.println("test 1");

        try {
            User existingUser = existingUserOptional.get();
            System.out.println("test 2");
            if (!file.isEmpty()) {
                System.out.println("test add picture pre");
                // Save the picture data in the database
                Pictures picture = new Pictures();

                picture.setPictureData(file.getBytes());
                System.out.println("test post set picture");

                picture.setId(UUID.randomUUID());
                pictureRepository.save(picture);

                System.out.println("post save picture");

                existingUser.setPictures(picture);

                System.out.println("test add picture post");
            }

            // Update other user fields
            existingUser.setName(user.getName());
            existingUser.setSurname(user.getSurname());
            existingUser.setGender(user.getGender());
            existingUser.setAge(user.getAge());
            System.out.println("test 3");

            userRepository.save(existingUser);

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
