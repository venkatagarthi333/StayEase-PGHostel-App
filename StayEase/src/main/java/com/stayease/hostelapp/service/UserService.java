package com.stayease.hostelapp.service;

import com.stayease.hostelapp.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);

    User findByEmail(String email);

    UserDetails loadUserByUsername(String email);

}
