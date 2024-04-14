package com.ridematefinder.service;

import com.ridematefinder.DriverRepository;
import com.ridematefinder.sql.Driver;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver addDriver(Driver driver) {
        if (driver.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "id should not be provided"
            );
        }
        driver.setId(UUID.randomUUID());
        return driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver updateDriver(Driver driver) {
        return driverRepository.findById(driver.getId())
                .map(existingDriver -> {
                    existingDriver.setName(driver.getName());
                    existingDriver.setDescription(driver.getDescription());
                    return driverRepository.save(existingDriver);
                }).orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + driver.getId()));

    }

    public void deleteDriver(UUID id) {
        driverRepository.findDriverById(id)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));
        driverRepository.deleteById(id);
    }

    public Driver getDriverById(UUID id) {
        return driverRepository.findDriverById(id)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));
    }

}
