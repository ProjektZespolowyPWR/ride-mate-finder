package com.ridematefinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ridematefinder.sql.Driver;
import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    @Autowired
    private DriverRepository driverRepository;

    @GetMapping
    public List<Driver> getAllCompanies() {
        return driverRepository.findAll();
    }

    @GetMapping("/{id}")
    public Driver getCompanyById(@PathVariable("id") int id) {
        return driverRepository.findById(id).orElse(null);
    }

    @PostMapping("")
    public Driver addCompany(@RequestBody Driver company) {
        return driverRepository.save(company);
    }

    @PutMapping("/{id}")
    public Driver updateCompany(@PathVariable("id") int id, @RequestBody Driver updatedCompany) {
        return driverRepository.findById(id)
                .map(company -> {
                    company.setName(updatedCompany.getName());
                    company.setDescription(updatedCompany.getDescription());
                    return driverRepository.save(company);
                }).orElseGet(() -> {
                    updatedCompany.setId(id);
                    return driverRepository.save(updatedCompany);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable("id") int id) {
        driverRepository.deleteById(id);
    }
}