package com.ridematefinder.repository;

import com.ridematefinder.sql.Car;
import com.ridematefinder.sql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
    List<Car> findAllByUser(User user);
}
