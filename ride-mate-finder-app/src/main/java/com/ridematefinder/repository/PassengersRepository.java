package com.ridematefinder.repository;

import com.ridematefinder.sql.Passengers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PassengersRepository extends JpaRepository<Passengers, UUID> {
}
