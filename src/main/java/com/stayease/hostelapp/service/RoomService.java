package com.stayease.hostelapp.service;

import com.stayease.hostelapp.model.PGHostel;
import com.stayease.hostelapp.model.Room;
import com.stayease.hostelapp.repository.RoomRepository;
import com.stayease.hostelapp.repository.PGHostelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PGHostelRepository pgHostelRepository;

    public Room addRoom(Long pgHostelId, Room room) {
        PGHostel pgHostel = pgHostelRepository.findById(pgHostelId)
                .orElseThrow(() -> new RuntimeException("PG Hostel not found"));

        room.setPgHostel(pgHostel);
        return roomRepository.save(room);
    }

    public Room updateRoom(Long roomId, Room updatedRoom) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setBedType(updatedRoom.getBedType());
        room.setTotalBeds(updatedRoom.getTotalBeds());
        room.setAvailableBeds(updatedRoom.getAvailableBeds());

        return roomRepository.save(room);
    }

    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    public List<Room> getRoomsByPG(Long pgHostelId) {
        PGHostel pgHostel = pgHostelRepository.findById(pgHostelId)
                .orElseThrow(() -> new RuntimeException("PG Hostel not found"));
        return pgHostel.getRooms(); // assuming PGHostel has `List<Room> rooms`
    }
}
