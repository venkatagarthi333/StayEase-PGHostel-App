package com.stayease.hostelapp.controller;

import com.stayease.hostelapp.model.PGHostel;
import com.stayease.hostelapp.model.Piligrim;
import com.stayease.hostelapp.model.Room;
import com.stayease.hostelapp.service.PiligrimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pilgrim")
public class PiligrimController {

    @Autowired
    private PiligrimService pilgrimService;

    //search hostels based on location
    @GetMapping("/search")
    public ResponseEntity<List<PGHostel>> searchHostels(@RequestParam String location) {
        List<PGHostel> hostels = pilgrimService.searchHostelsByLocation(location);
        return ResponseEntity.ok(hostels);
    }


    //gets rooms of hostels
    @GetMapping("/rooms/{hostelId}")
    public ResponseEntity<List<Room>> getRooms(@PathVariable Long hostelId) {
        List<Room> rooms = pilgrimService.getRoomsByHostel(hostelId);
        return ResponseEntity.ok(rooms);
    }


    //books room
    @PostMapping("/book/{roomId}")
    @PreAuthorize("hasRole('PILGRIM')")
    public ResponseEntity<?> bookRoom(@AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable Long roomId) {
        Piligrim booking = pilgrimService.bookRoom(userDetails.getUsername(), roomId);
        return ResponseEntity.ok("room booked successfully");
    }

    //check out and cancel room
    @DeleteMapping("/cancel-booking")
    @PreAuthorize("hasRole('PILGRIM')")
    public ResponseEntity<?> cancelBooking(@AuthenticationPrincipal UserDetails userDetails) {
        pilgrimService.cancelBooking(userDetails.getUsername());
        return ResponseEntity.ok("Booking cancelled successfully");
    }
}
