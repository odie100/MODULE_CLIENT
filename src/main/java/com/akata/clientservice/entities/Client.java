package com.akata.clientservice.entities;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String username;
    private String password;
    private String name;
    private String type;
    @Lob
    private String description;
    private LocalDate creation;
    private String activated;
    @ManyToOne()
    @JoinColumn(name = "location_id")
    Location location;
    private String photo;
}
