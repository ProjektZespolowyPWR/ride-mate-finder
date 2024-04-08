package com.ridematefinder;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    private List<Driver> drivers = new ArrayList<>(Arrays.asList(new Driver(1, "Jan", "example driver 1"), new Driver(2, "Andrew", "example driver 2")));
    @GetMapping
    public List<Driver> getAllDrivers() {
        return drivers;
    }

    @GetMapping("/{id}")
    public Driver getDriverById(@PathVariable("id") int id) {
        for(Driver driver : drivers){
            if(driver.id == id){
                return driver;
            }
        }
        return null;
    }

    @PostMapping("")
    public void addDriver(@RequestBody Driver driver) {
        drivers.add(driver);
    }

    @PutMapping("/{id}")
    public void updateDriver(@PathVariable("id") int id, @RequestBody Driver updatedDriver) {
        Driver driver = getDriverById(id);
        if (driver != null) {
            driver.name = updatedDriver.name;
            driver.description = updatedDriver.description;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable("id") int id) {
        Driver driverToDelete = getDriverById(id);
        drivers.remove(driverToDelete);
    }
}