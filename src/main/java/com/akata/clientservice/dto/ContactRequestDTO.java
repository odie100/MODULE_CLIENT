package com.akata.clientservice.dto;

import com.akata.clientservice.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ContactRequestDTO {
    String type;
    String value;
    Client client;
}
