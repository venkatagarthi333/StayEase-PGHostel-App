package com.stayease.hostelapp.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Piligrim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;


    @ManyToOne
    @JoinColumn(name = "room_id")

    private Room room;

    @ManyToOne
    @JoinColumn(name = "pg_hostel_id")
    private PGHostel pgHostel;


}
