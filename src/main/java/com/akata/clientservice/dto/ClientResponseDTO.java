package com.akata.clientservice.dto;

import com.akata.clientservice.entities.Location;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class ClientResponseDTO {
    private Long id;
    private String username;
    private Location location;
    private String name;
    private String type;
    private String description;
    private String photo;
    private String email;
    private String phone;
    private LocalDate creation;
}
