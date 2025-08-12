// LogoutService.java
package com.stayease.hostelapp.service;

import com.stayease.hostelapp.model.BlacklistedToken;
import com.stayease.hostelapp.repository.BlacklistedTokenRepository;
import com.stayease.hostelapp.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LogoutService {

    private final BlacklistedTokenRepository blacklistRepo;
    private final JwtUtil jwtUtil;

    public LogoutService(BlacklistedTokenRepository blacklistRepo, JwtUtil jwtUtil) {
        this.blacklistRepo = blacklistRepo;
        this.jwtUtil = jwtUtil;
    }

    public void blacklistAccessToken(String accessToken) {
        Instant expiry = jwtUtil.getExpiryFromToken(accessToken);
        blacklistRepo.save(new BlacklistedToken(accessToken, expiry));
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistRepo.findByToken(token).isPresent();
    }

    @Transactional
    @Scheduled(fixedRate = 3600000) // 1 hour in milliseconds
    public void deleteExpiredTokens() {
        var now = Instant.now();
        var expiredTokens = blacklistRepo.findAll().stream()
                .filter(token -> token.getExpiryDate().isBefore(now))
                .toList();
        blacklistRepo.deleteAll(expiredTokens);
    }
}
