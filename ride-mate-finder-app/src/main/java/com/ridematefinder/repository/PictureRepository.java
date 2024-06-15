package com.ridematefinder.repository;

import com.ridematefinder.sql.Pictures;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PictureRepository extends JpaRepository<Pictures, UUID> {
    Optional<Pictures> findPictureById(UUID id);
}
