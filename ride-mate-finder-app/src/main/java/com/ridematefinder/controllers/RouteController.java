package com.ridematefinder.controllers;

import com.ridematefinder.sql.Route;
import com.ridematefinder.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
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
}
