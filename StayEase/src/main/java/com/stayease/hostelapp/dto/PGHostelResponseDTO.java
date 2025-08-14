package com.stayease.hostelapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PGHostelResponseDTO {
    private Long id;
    private String hostelName;
    private String location;
    private String contactNumber;

}

