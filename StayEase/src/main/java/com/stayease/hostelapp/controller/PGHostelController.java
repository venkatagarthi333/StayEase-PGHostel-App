package com.stayease.hostelapp.controller;

import com.stayease.hostelapp.dto.PGHostelResponseDTO;
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
        PGHostelResponseDTO response = new PGHostelResponseDTO(
                createdHostel.getId(),
                createdHostel.getHostelName(),
                createdHostel.getLocation(),
                createdHostel.getContactNumber()
        );
        return ResponseEntity.ok(response);
    }

    //get all hostels
    @GetMapping("/my-hostels")
    public ResponseEntity<List<PGHostelResponseDTO>> getMyHostels(@AuthenticationPrincipal UserDetails userDetails) {
        List<PGHostel> hostels = pgHostelService.getHostelsByOwner(userDetails.getUsername());
        List<PGHostelResponseDTO> responseDTOS = hostels.stream()
                .map(h -> new PGHostelResponseDTO(
                        h.getId(),
                        h.getHostelName(),
                        h.getLocation(),
                        h.getContactNumber()
                ))
                .toList();


        return ResponseEntity.ok(responseDTOS);
    }

    // Update hostel
    @PutMapping("/update-hostel/{hostelId}")
    public ResponseEntity<?> updateHostel(@AuthenticationPrincipal UserDetails userDetails,
                                          @PathVariable Long hostelId,
                                          @RequestBody PGHostel pgHostel) {
        PGHostel updatedHostel = pgHostelService.updateHostel(userDetails.getUsername(), hostelId, pgHostel);
        return ResponseEntity.ok("Hostel updated successfully");
    }

    // Delete hostel
    @DeleteMapping("/delete-hostel/{hostelId}")
    public ResponseEntity<?> deleteHostel(@AuthenticationPrincipal UserDetails userDetails,
                                          @PathVariable Long hostelId) {
        pgHostelService.deleteHostel(userDetails.getUsername(), hostelId);
        return ResponseEntity.ok("Hostel deleted successfully");
    }
}
