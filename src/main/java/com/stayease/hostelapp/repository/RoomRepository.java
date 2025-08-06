package com.stayease.hostelapp.repository;

import com.stayease.hostelapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
