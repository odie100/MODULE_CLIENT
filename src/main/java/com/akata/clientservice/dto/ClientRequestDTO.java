package com.akata.clientservice.dto;

import com.akata.clientservice.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ClientRequestDTO {
    private String username;
    private String password;
    private Location location;
    private String type;
    private String name;
    private LocalDate creation;
}
