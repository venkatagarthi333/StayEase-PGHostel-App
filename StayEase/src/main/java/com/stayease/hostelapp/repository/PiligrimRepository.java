package com.stayease.hostelapp.repository;

import com.stayease.hostelapp.model.Piligrim;
import com.stayease.hostelapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PiligrimRepository extends JpaRepository<Piligrim, Long> {

    Optional<Piligrim> findByUser(User user);

}
