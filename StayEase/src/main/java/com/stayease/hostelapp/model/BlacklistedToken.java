// BlacklistedToken.java
package com.stayease.hostelapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "blacklisted_tokens")
public class BlacklistedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    public BlacklistedToken() {}

    public BlacklistedToken(String token, Instant expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
    }
}
