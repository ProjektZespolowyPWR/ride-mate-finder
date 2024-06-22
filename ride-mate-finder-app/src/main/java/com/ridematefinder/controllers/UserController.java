package com.ridematefinder.controllers;

import com.ridematefinder.repository.*;
import com.ridematefinder.sql.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.google.api-key}")
    private String googleApiKey;

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final CarRepository carRepository;
    private final PassengersRepository passengersRepository;
    private final RouteRepository routeRepository;

    public UserController(UserRepository userRepository, PictureRepository pictureRepository, CarRepository carRepository, PassengersRepository passengersRepository, RouteRepository routeRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;
        this.passengersRepository = passengersRepository;
        this.routeRepository = routeRepository;
    }

    @GetMapping("/profileForm")
    public String showProfileForm(Model model, HttpSession session ) {
        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> { model.addAttribute("LoginUser", value);

            if (value.getPictures() != null) {
                byte[] pictureData = value.getPictures().getPictureData();;
                String base64Image = Base64.getEncoder().encodeToString(pictureData);
                model.addAttribute("userImage", base64Image);
            }
        });
        model.addAttribute("user", user.orElse(new User()));
        return "profileForm";
    }

    @GetMapping("/showUserProfile")
    public String showProfile(Model model, HttpSession session){
        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> { model.addAttribute("LoginUser", value);
            if (value.getPictures() != null) {
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

        List<Route> routesList = routeRepository.findAllByUser(user.get());
        List<Passengers> passengersList = passengersRepository.findAll();
        List<Passengers> newPassengersList = new ArrayList<>();
        for (Passengers passengers : passengersList) {
            if(routesList.contains(passengers.getRoute())) {
                newPassengersList.add(passengers);
            }
        }
        model.addAttribute("passengers", newPassengersList);

        return "profile";
    }

    @GetMapping("/showAcceptForm")
    public String showAcceptForm(@RequestParam UUID id, Model model, HttpSession session) {
        Optional<Passengers> passenger = passengersRepository.findById(id);
        passenger.ifPresent(value -> model.addAttribute("passenger", value));
        model.addAttribute("googleApiKey", googleApiKey);
        return "acceptForm";
    }

    @PostMapping("/acceptPassenger")
    public String acceptPassenger(@RequestParam UUID id, @RequestParam Integer accepted) {
        Optional<Passengers> passenger = passengersRepository.findById(id);
        passenger.ifPresent(value -> {
            if(accepted == 0){
                passengersRepository.delete(value);
            } else {
                value.setAccepted(accepted);
                value.getRoute().setAvailableSeats(value.getRoute().getAvailableSeats() - 1);
                passengersRepository.save(value);
            }
        });
        return "redirect:/showUserProfile";
    }

    @PostMapping("/updateUserData")
    public String addUserData(@Valid User user, BindingResult result,
                              @RequestParam(value = "isDriver", required = false) String isDriverValue,
                              @RequestParam("file") MultipartFile file, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "profileForm";
        }
        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> existingUserOptional = userRepository.findById(userId);

        if (existingUserOptional.isEmpty()) {
            model.addAttribute("errorMessage", "User not found.");
            return "profileForm";
        }

        try {
            User existingUser = existingUserOptional.get();
            if (!file.isEmpty()) {
                Pictures picture = new Pictures();
                picture.setPictureData(file.getBytes());
                picture.setId(UUID.randomUUID());
                pictureRepository.save(picture);
                existingUser.setPictures(picture);
            }

            existingUser.setName(user.getName());
            existingUser.setSurname(user.getSurname());
            existingUser.setGender(user.getGender());
            existingUser.setAge(user.getAge());

            int isDriver = Integer.parseInt(isDriverValue);
            if (isDriver == 1) {
                session.setAttribute("isDriver", isDriver);
            } else {
                session.removeAttribute("isDriver");
            }
            existingUser.setIsDriver(isDriver);
            userRepository.save(existingUser);
            return "redirect:/showUserProfile";
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
        session.removeAttribute("isDriver");
        return "redirect:/";
    }

}
