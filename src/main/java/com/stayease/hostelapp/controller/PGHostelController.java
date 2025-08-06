package com.stayease.hostelapp.controller;

import com.stayease.hostelapp.model.*;
import com.stayease.hostelapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pg")
public class PGHostelController {

    @Autowired
    private PGHostelRepository pgHostelRepo;

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/add-hostel")
    public ResponseEntity<String> createPGHostel(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody PGHostel hostelData) {

        /*User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow();
        User user = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userDetails.getUsername()));*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userOptional.get();



        PGHostel hostel = new PGHostel();
        hostel.setUser(user);
        hostel.setHostelName(hostelData.getHostelName());
        hostel.setLocation(hostelData.getLocation());
        hostel.setContactNumber(hostelData.getContactNumber());

        pgHostelRepo.save(hostel);

        return ResponseEntity.ok("PG Hostel created successfully");
    }


    @PostMapping("/add-room")
    public ResponseEntity<String> addRoom(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody Room room) {

        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow();

        PGHostel hostel = pgHostelRepo.findByUserId(user.getId()).orElseThrow(() ->
                new RuntimeException("PG Hostel not found for user"));

        room.setPgHostel(hostel);
        roomRepo.save(room);

        return ResponseEntity.ok("Room added successfully");
    }

    @GetMapping("/my-rooms")
    public ResponseEntity<List<Room>> getMyRooms(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow();

        PGHostel hostel = pgHostelRepo.findByUserId(user.getId()).orElseThrow();

        return ResponseEntity.ok(hostel.getRooms());
    }
}
