package com.stayease.hostelapp.controller;

import com.stayease.hostelapp.dto.LoginRequest;
import com.stayease.hostelapp.model.User;
import com.stayease.hostelapp.dto.AuthResponse;
import com.stayease.hostelapp.security.JwtUtil;
import com.stayease.hostelapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .get()
                    .getAuthority();

            String token = jwtUtil.generateToken(userDetails.getUsername(), role);

            return ResponseEntity.ok(new AuthResponse(token, "Login Successful"));

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }

}
