package com.stayease.hostelapp.service;

import com.stayease.hostelapp.model.PGHostel;
import com.stayease.hostelapp.model.User;
import com.stayease.hostelapp.repository.PGHostelRepository;
import com.stayease.hostelapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PGHostelService {

    @Autowired
    private PGHostelRepository pgHostelRepository;

    @Autowired
    private UserRepository userRepository;
    //add new hostel
    public PGHostel createHostel(String email, PGHostel pgHostel) {
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Now multiple hostels allowed for one owner
        pgHostel.setUser(owner);

        return pgHostelRepository.save(pgHostel);
    }
    //get all hostel's
    public List<PGHostel> getHostelsByOwner(String email) {
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return pgHostelRepository.findByUser(owner);
    }
    // Update hostel
    public PGHostel updateHostel(String email, Long hostelId, PGHostel updatedHostel) {
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PGHostel existingHostel = pgHostelRepository.findById(hostelId)
                .orElseThrow(() -> new RuntimeException("Hostel not found"));

        // Ensure the logged-in owner owns this hostel
        if (!existingHostel.getUser().getId().equals(owner.getId())) {
            throw new RuntimeException("You are not authorized to update this hostel");
        }

        existingHostel.setHostelName(updatedHostel.getHostelName());
        existingHostel.setLocation(updatedHostel.getLocation());
        existingHostel.setContactNumber(updatedHostel.getContactNumber());

        return pgHostelRepository.save(existingHostel);
    }

    // Delete hostel
    public void deleteHostel(String email, Long hostelId) {
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PGHostel existingHostel = pgHostelRepository.findById(hostelId)
                .orElseThrow(() -> new RuntimeException("Hostel not found"));

        if (!existingHostel.getUser().getId().equals(owner.getId())) {
            throw new RuntimeException("You are not authorized to delete this hostel");
        }

        pgHostelRepository.delete(existingHostel);
    }
}
