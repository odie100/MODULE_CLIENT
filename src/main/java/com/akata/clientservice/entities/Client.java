package com.akata.clientservice.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String username;
    private String password;
    private String name;
    private String type;
    private String description;
    private Long location_id;
    private LocalDate creation;
    @ManyToOne()
    @JoinColumn(name = "location_id")
    Location location;
}
