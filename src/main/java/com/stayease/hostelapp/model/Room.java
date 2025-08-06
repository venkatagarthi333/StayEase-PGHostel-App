package com.stayease.hostelapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType; // E.g. "2-sharing", "3-sharing"
    private int totalBeds;
    private int availableBeds;

    @ManyToOne
    @JoinColumn(name = "pg_hostel_id")
    private PGHostel pgHostel;
}
