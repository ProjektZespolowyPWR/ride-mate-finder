package com.ridematefinder.controllers;

import com.ridematefinder.repository.RouteRepository;
import com.ridematefinder.sql.Route;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;


@Controller
public class CityController {

    private final RouteRepository routeRepository;

    public CityController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping("/routes/submit_city")
    public String handleCitySubmission(@RequestParam UUID id, Model model) {
        Optional<Route> route = routeRepository.findById(id);
        route.ifPresent(r -> model.addAttribute("route", r));
        return "stop_form";
    }
}
