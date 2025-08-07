package com.stayease.hostelapp.controller;

import com.stayease.hostelapp.model.Piligrim;
import com.stayease.hostelapp.service.PiligrimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pilgrim")
public class PiligrimController {

    @Autowired
    private PiligrimService pilgrimService;

    @PostMapping("/book/{roomId}")
    @PreAuthorize("hasRole('PILGRIM')")
    public ResponseEntity<?> bookRoom(@AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable Long roomId) {
        Piligrim booking = pilgrimService.bookRoom(userDetails.getUsername(), roomId);
        return ResponseEntity.ok("room booked successfully");
    }
}
