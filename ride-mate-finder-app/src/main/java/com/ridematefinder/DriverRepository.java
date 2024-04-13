package com.ridematefinder;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ridematefinder.sql.Driver;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
}
