package com.stayease.hostelapp.repository;

import com.stayease.hostelapp.model.PGHostel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PGHostelRepository extends JpaRepository<PGHostel, Long> {
    Optional<PGHostel> findByUserId(Long userId);
}
