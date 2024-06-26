package com.ridematefinder.controllers;

import com.ridematefinder.repository.CarRepository;
import com.ridematefinder.repository.UserRepository;
import com.ridematefinder.sql.Car;
import com.ridematefinder.sql.Route;
import com.ridematefinder.repository.RouteRepository;
import com.ridematefinder.sql.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/routes")
public class RouteController {

    @Value("${spring.google.api-key}")
    private String googleApiKey;


    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Autowired
    public RouteController(RouteRepository routeRepository, UserRepository userRepository, CarRepository carRepository) {
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @GetMapping
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    @GetMapping("/all")
    public String viewAllRoutes(Model model) {
        model.addAttribute("routes", routeRepository.findAll());
        model.addAttribute("googleApiKey", googleApiKey);
        return "routes";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable UUID id) {
        Optional<Route> route = routeRepository.findById(id);
        return route.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Route createRoute(@RequestBody Route route) {
        return routeRepository.save(route);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable UUID id, @RequestBody Route routeDetails) {
        Optional<Route> routeOptional = routeRepository.findById(id);
        if (routeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Route route = routeOptional.get();
        route.setStartPoint(routeDetails.getStartPoint());
        route.setEndPoint(routeDetails.getEndPoint());
        route.setDateOfRide(routeDetails.getDateOfRide());
        route.setUrlToMaps(routeDetails.getUrlToMaps());
        route.setAvailableSeats(routeDetails.getAvailableSeats());
        Route updatedRoute = routeRepository.save(route);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable UUID id) {
        if (!routeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        routeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/addRouteForm")
    public String showAddRouteForm(Model model, HttpSession session) {
        User user = userRepository.findById((UUID) session.getAttribute("userId")).get();
        List<Car> carsList = carRepository.findAllByUser(user);
        model.addAttribute("cars", carsList);
        return "addRouteForm";
    }

    @PostMapping("/addRoute")
    public String addRoute(@RequestParam("startCity") String startCity,
                           @RequestParam("startStreet") String startStreet,
                           @RequestParam("endCity") String endCity,
                           @RequestParam("endStreet") String endStreet,
                           @ModelAttribute Route route, Model model,
                           @RequestParam("car") UUID carId,
                           HttpSession session) {

        String startPoint = startCity.trim() + ", " + startStreet.trim();
        String endPoint = endCity.trim() + ", " + endStreet.trim();

        route.setStartPoint(startPoint);
        route.setEndPoint(endPoint);
        route.setId(UUID.randomUUID());
        User user = userRepository.findById((UUID) session.getAttribute("userId")).get();
        route.setUser(user);
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null) {
            route.setCar(car);
        }

        routeRepository.save(route);

        model.addAttribute("message", "Route successfully added!");
        return "redirect:/showUserProfile";
    }
}
