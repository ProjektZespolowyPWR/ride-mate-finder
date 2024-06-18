package com.ridematefinder.repository;

import com.ridematefinder.sql.Opinionuser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OpinionUserRepository extends JpaRepository<Opinionuser, UUID> {

}
