package com.stayease.hostelapp.service;

import com.stayease.hostelapp.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface UserService {
    User registerUser(User user);

    UserDetails loadUserByUsername(String email);
}
