package com.ridematefinder.driverController;


import com.ridematefinder.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ridematefinder.sql.Driver;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/driver")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable("id") UUID id) {
        Driver driver = driverService.getDriverById(id);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDriver(@RequestBody Driver driver) {
        Driver newDriver = driverService.addDriver(driver);
        return new ResponseEntity<>(newDriver, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateDriver(@RequestBody Driver updatedDriver) {
        Driver updateDriver = driverService.updateDriver(updatedDriver);
        return new ResponseEntity<>(updateDriver, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable("id") UUID id) {
        driverService.deleteDriver(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
