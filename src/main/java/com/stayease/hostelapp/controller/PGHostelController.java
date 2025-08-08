package com.stayease.hostelapp.controller;

import com.stayease.hostelapp.model.PGHostel;
import com.stayease.hostelapp.service.PGHostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@PreAuthorize("hasRole('PG_OWNER')")
@RestController
@RequestMapping("/api/pg")
public class PGHostelController {

    @Autowired
    private PGHostelService pgHostelService;

    //add hostel
    @PostMapping("/add-hostel")
    public ResponseEntity<?> createHostel(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody PGHostel pgHostel) {
        PGHostel createdHostel = pgHostelService.createHostel(userDetails.getUsername(), pgHostel);
        return ResponseEntity.ok(createdHostel);
    }

    //get all hostels
    @GetMapping("/my-hostels")
    public ResponseEntity<List<PGHostel>> getMyHostels(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(pgHostelService.getHostelsByOwner(userDetails.getUsername()));
    }

    // Update hostel
    @PutMapping("/update-hostel/{hostelId}")
    public ResponseEntity<?> updateHostel(@AuthenticationPrincipal UserDetails userDetails,
                                          @PathVariable Long hostelId,
                                          @RequestBody PGHostel pgHostel) {
        PGHostel updatedHostel = pgHostelService.updateHostel(userDetails.getUsername(), hostelId, pgHostel);
        return ResponseEntity.ok(updatedHostel);
    }

    // Delete hostel
    @DeleteMapping("/delete-hostel/{hostelId}")
    public ResponseEntity<?> deleteHostel(@AuthenticationPrincipal UserDetails userDetails,
                                          @PathVariable Long hostelId) {
        pgHostelService.deleteHostel(userDetails.getUsername(), hostelId);
        return ResponseEntity.ok("Hostel deleted successfully");
    }
}
