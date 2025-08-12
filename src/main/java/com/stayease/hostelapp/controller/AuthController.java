package com.stayease.hostelapp.controller;

import com.stayease.hostelapp.dto.LoginRequest;
import com.stayease.hostelapp.dto.AuthResponse;
import com.stayease.hostelapp.model.RefreshToken;
import com.stayease.hostelapp.model.User;
import com.stayease.hostelapp.security.JwtUtil;
import com.stayease.hostelapp.service.LogoutService;
import com.stayease.hostelapp.service.RefreshTokenService;
import com.stayease.hostelapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
        String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

        String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername(), role);

        // Fix here: unwrap Optional<User> safely
        User user = userService.findByEmail(userDetails.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken.getToken()
        ));
    }


    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String requestRefreshToken = request.get("refreshToken");

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String role = user.getRole().name();
                    String newAccessToken = jwtUtil.generateAccessToken(user.getEmail(), role);
                    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    @Autowired
    private LogoutService logoutService;

    // Logout — delete refresh token
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody Map<String, String> request) {

        String requestRefreshToken = request.get("refreshToken");

        // Delete refresh token
        refreshTokenService.findByToken(requestRefreshToken)
                .ifPresent(token -> refreshTokenService.deleteByUserId(token.getUser().getId()));

        // Blacklist access token if present
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.substring(7);
            logoutService.blacklistAccessToken(accessToken);
        }

        return ResponseEntity.ok("Log out successful!");
    }
}
