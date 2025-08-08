package com.stayease.hostelapp.controller;

import com.stayease.hostelapp.model.Room;
import com.stayease.hostelapp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('PG_OWNER')")
@RestController
@RequestMapping("/api/pg/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    //add room
    @PreAuthorize("hasRole('PG_OWNER')")
    @PostMapping("add-room/{pgHostelId}")
    public Room addRoom(@PathVariable Long pgHostelId, @RequestBody Room room) {
        return roomService.addRoom(pgHostelId, room);
    }

    //update room
    @PutMapping("update-room/{roomId}")
    public Room updateRoom(@PathVariable Long roomId, @RequestBody Room room) {
        return roomService.updateRoom(roomId, room);
    }

    //delete room
    @DeleteMapping("delete-room/{roomId}")
    public void deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
    }

    //get all rooms
    @GetMapping("/get-rooms/{pgHostelId}")
    public List<Room> getRoomsByPG(@PathVariable Long pgHostelId) {
        return roomService.getRoomsByPG(pgHostelId);
    }
}
