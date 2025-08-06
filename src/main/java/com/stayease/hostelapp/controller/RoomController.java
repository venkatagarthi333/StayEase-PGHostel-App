package com.stayease.hostelapp.controller;

import com.stayease.hostelapp.model.Room;
import com.stayease.hostelapp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('PG_OWNER')")
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PreAuthorize("hasRole('PG_OWNER')")
    @PostMapping("/{pgHostelId}")
    public Room addRoom(@PathVariable Long pgHostelId, @RequestBody Room room) {
        return roomService.addRoom(pgHostelId, room);
    }

    @PutMapping("/{roomId}")
    public Room updateRoom(@PathVariable Long roomId, @RequestBody Room room) {
        return roomService.updateRoom(roomId, room);
    }

    @DeleteMapping("/{roomId}")
    public void deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
    }

    @GetMapping("/pg/{pgHostelId}")
    public List<Room> getRoomsByPG(@PathVariable Long pgHostelId) {
        return roomService.getRoomsByPG(pgHostelId);
    }
}
