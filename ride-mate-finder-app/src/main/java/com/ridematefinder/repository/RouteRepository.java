package com.ridematefinder.repository;

import com.ridematefinder.sql.Route;
import com.ridematefinder.sql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID> {
    List<Route> findAllByUser(User user);
}
