package com.stayease.hostelapp.repository;

import com.stayease.hostelapp.model.RefreshToken;
import com.stayease.hostelapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
}
