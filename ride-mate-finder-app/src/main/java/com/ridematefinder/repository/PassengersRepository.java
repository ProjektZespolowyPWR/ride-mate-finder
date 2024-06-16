package com.ridematefinder.repository;

import com.ridematefinder.sql.Passengers;
import com.ridematefinder.sql.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PassengersRepository extends JpaRepository<Passengers, UUID> {
    List<Passengers> findAllByRoute(Route route);
}
