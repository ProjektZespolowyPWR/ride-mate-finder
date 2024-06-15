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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
public class CarController {

    private final CarRepository carRepository;
    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;

    public CarController(CarRepository carRepository, PictureRepository pictureRepository,
                         UserRepository userRepository) {
        this.carRepository = carRepository;
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/carForm")
    public String carForm(Model model, HttpSession session) {
        model.addAttribute("car", new Car());
        return "carForm";
    }


    @PostMapping("/addCar")
    public String addCar(@Valid Car car, BindingResult result, @RequestParam("file") MultipartFile file,
                         Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "carForm";
        }

        try {
            // Save the car picture if provided
            if (!file.isEmpty()) {
                Pictures picture = new Pictures();
                picture.setPictureData(file.getBytes());
                picture.setId(UUID.randomUUID());
                pictureRepository.save(picture);
                car.setPictures(picture);
            }

            // Get user ID from session and set it to car
            UUID userId = (UUID) session.getAttribute("userId");
            User user = userRepository.findById(userId).orElseThrow();
            car.setUser(user);

            // Save the car to the database
            car.setId(UUID.randomUUID());
            carRepository.save(car);

            return "redirect:/showUserProfile";  // Redirect to a page that shows user's cars
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error uploading picture. Please try again.");
            return "carForm";
        }
    }

}
