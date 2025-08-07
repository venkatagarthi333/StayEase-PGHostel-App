package com.stayease.hostelapp.service;

import com.stayease.hostelapp.model.*;
import com.stayease.hostelapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PiligrimService {

    @Autowired
    private PiligrimRepository pilgrimRepo;

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private PGHostelRepository pgHostelRepo;

    @Autowired
    private UserRepository userRepo;

    public Piligrim bookRoom(String userEmail, Long roomId) {
        User user = userRepo.findByEmail(userEmail).orElseThrow();

        Room room = roomRepo.findById(roomId).orElseThrow();

        if (room.getAvailableBeds() <= 0) {
            throw new RuntimeException("No beds available in this room");
        }

        PGHostel pgHostel = room.getPgHostel();

        Piligrim pilgrim = new Piligrim();
        pilgrim.setUser(user);
        pilgrim.setRoom(room);
        pilgrim.setPgHostel(pgHostel);

        room.setAvailableBeds(room.getAvailableBeds() - 1); // reduce 1 bed

        roomRepo.save(room);
        return pilgrimRepo.save(pilgrim);
    }
}
