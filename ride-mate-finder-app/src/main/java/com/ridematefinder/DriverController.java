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
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverRepository.findAll();
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable("id") UUID id) {
        Driver driver = driverRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Driver not found with id: " + id));
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addDriver(@RequestBody Driver driver) {
        if (driver.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "id should not be provided"
            );
        }
        driver.setId(UUID.randomUUID());
        Driver savedDriver = driverRepository.save(driver);
        return new ResponseEntity<>(savedDriver, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable("id") UUID id) {
        return driverRepository.findById(id).map(driver -> {
            driverRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable("id") UUID id, @RequestBody Driver updatedDriver) {
        Driver driver = driverRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setName(updatedDriver.getName());
                    existingCompany.setDescription(updatedDriver.getDescription());
                    return driverRepository.save(existingCompany);
                }).orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));

        return new ResponseEntity<>(driver, HttpStatus.OK);
    }
}