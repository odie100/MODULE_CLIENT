package com.akata.clientservice.dto;

import com.akata.clientservice.entities.Location;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class ClientRequestDTO {
    private String username;
    private String password;
    private Location location;
    private String type;
    private String name;
    private String activated;
    private String description;
    private LocalDate creation;
}
