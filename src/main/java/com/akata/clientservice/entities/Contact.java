package com.akata.clientservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String type;
    String value;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    Client client;
}
