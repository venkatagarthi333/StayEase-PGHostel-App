package com.stayease.hostelapp.repository;

import com.stayease.hostelapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByPgHostelId(Long pgHostelId);
}
