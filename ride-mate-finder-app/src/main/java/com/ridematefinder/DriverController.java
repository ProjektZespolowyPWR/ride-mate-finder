package com.ridematefinder;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ridematefinder.sql.Driver;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    @Autowired
    private DriverRepository driverRepository;

    @GetMapping
    public ResponseEntity<List<Driver>> getAllCompanies() {
        List<Driver> companies = driverRepository.findAll();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable("id") UUID id) {
        Driver Driver = driverRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Driver not found with id: " + id));
        return new ResponseEntity<>(Driver, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addDriver(@RequestBody Driver Driver) {
        if (Driver.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "id should not be provided"
            );
        }
        Driver.setId(UUID.randomUUID());
        Driver savedDriver = driverRepository.save(Driver);
        return new ResponseEntity<>(savedDriver, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable("id") UUID id) {
        return driverRepository.findById(id).map(Driver -> {
            driverRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDriver(@PathVariable("id") UUID id, @RequestBody Driver updatedDriver) {
        Driver Driver = driverRepository.findById(id)
                .map(existingDriver -> {
                    existingDriver.setName(updatedDriver.getName());
                    existingDriver.setDescription(updatedDriver.getDescription());
                    return driverRepository.save(existingDriver);
                }).orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));

        return new ResponseEntity<>(Driver, HttpStatus.OK);
    }
}