package com.stayease.hostelapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PGHostel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hostelName;
    private String location;
    private String contactNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // The owner of the PG

    @OneToMany(mappedBy = "pgHostel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;
}
