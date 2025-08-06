package com.stayease.hostelapp.controller;

import com.stayease.hostelapp.model.*;
import com.stayease.hostelapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pg")
public class PGHostelController {

    @Autowired
    private PGHostelRepository pgHostelRepo;

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private UserRepository userRepo;

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
