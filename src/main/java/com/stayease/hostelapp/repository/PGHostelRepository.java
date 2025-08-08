package com.stayease.hostelapp.repository;

import com.stayease.hostelapp.model.PGHostel;
import com.stayease.hostelapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PGHostelRepository extends JpaRepository<PGHostel, Long> {
    Optional<PGHostel> findByUserId(Long userId);
    List<PGHostel> findByUser(User user);
}
