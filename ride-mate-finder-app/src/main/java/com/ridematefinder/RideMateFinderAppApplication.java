package com.ridematefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.ridematefinder.sql"})
@EnableJpaRepositories(basePackages = {"com.ridematefinder"})
public class RideMateFinderAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(RideMateFinderAppApplication.class, args);
	}

}
