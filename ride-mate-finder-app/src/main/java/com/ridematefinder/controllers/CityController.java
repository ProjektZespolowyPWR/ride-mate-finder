package com.ridematefinder.controllers;

import com.ridematefinder.repository.PassengersRepository;
import com.ridematefinder.repository.RouteRepository;
import com.ridematefinder.repository.UserRepository;
import com.ridematefinder.sql.Passengers;
import com.ridematefinder.sql.Route;
import com.ridematefinder.sql.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;


@Controller
public class CityController {

    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final PassengersRepository passengersRepository;

    public CityController(RouteRepository routeRepository, UserRepository userRepository, PassengersRepository passengersRepository) {
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.passengersRepository = passengersRepository;
    }

    @GetMapping("/routes/submit_city")
    public String handleCitySubmission(@RequestParam UUID id, Model model) {
        Optional<Route> route = routeRepository.findById(id);
        route.ifPresent(r -> model.addAttribute("route", r));
        return "stop_form";
    }

    @PostMapping("/routes/submit_city")
    public String addPassenger(@ModelAttribute Passengers passengerForm, @RequestParam UUID routeId,
                               @RequestParam("cityName") String city,
                               @RequestParam("address") String spot, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");
        Optional<User> user = userRepository.findById(userId);
        Optional<Route> route = routeRepository.findById(routeId);
        String fullAddress = spot + "," + city;

        if (user.isPresent() && route.isPresent()) {
            Passengers newPassenger = new Passengers(UUID.randomUUID(), user.get(), route.get(), fullAddress, 0);
            passengersRepository.save(newPassenger);
            return "redirect:/routes/all";  // Redirect after post success
        }
        return "redirect:/errorPage";  // Redirect in case of error
    }

}
