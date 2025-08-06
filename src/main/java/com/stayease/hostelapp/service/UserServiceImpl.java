package com.stayease.hostelapp.service;

import com.stayease.hostelapp.model.User;
import com.stayease.hostelapp.repository.UserRepository;
import com.stayease.hostelapp.security.UserDetailsServiceImpl;
import com.stayease.hostelapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        // You can add password encoding or validations here
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        // Delegate to your Spring Security login service
        return userDetailsService.loadUserByUsername(email);
    }
}
