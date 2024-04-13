package com.ridematefinder;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ridematefinder.sql.Driver;

import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
}
