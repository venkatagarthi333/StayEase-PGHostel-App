package com.stayease.hostelapp.service;

import com.stayease.hostelapp.model.*;
import com.stayease.hostelapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PiligrimService {

    @Autowired
    private PiligrimRepository pilgrimRepo;

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private PGHostelRepository pgHostelRepository;

    @Autowired
    private UserRepository userRepo;

    //search hostels based on location
    public List<PGHostel> searchHostelsByLocation(String location) {
        return pgHostelRepository.findByLocationIgnoreCase(location);
    }


    //get rooms of hostels
    public List<Room> getRoomsByHostel(Long hostelId) {
        return roomRepo.findByPgHostelId(hostelId);
    }


    //book room
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


        roomRepo.save(room);
        room.setAvailableBeds(room.getAvailableBeds() - 1); // reduce 1 bed
        return pilgrimRepo.save(pilgrim);
    }

    //check out room
    public void cancelBooking(String userEmail) {
        User user = userRepo.findByEmail(userEmail).orElseThrow();

        // Find pilgrim booking for this user
        Piligrim pilgrim = pilgrimRepo.findByUser(user).orElseThrow();

        Room room = pilgrim.getRoom();


        room.setAvailableBeds(room.getAvailableBeds() + 1);

        roomRepo.save(room);

        // Delete pilgrim booking
        pilgrimRepo.delete(pilgrim);
    }
}
