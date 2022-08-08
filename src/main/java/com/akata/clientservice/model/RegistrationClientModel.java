package com.akata.clientservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RegistrationClientModel {
    String name;
    String country;
    String address;
    String town;
    String username;
    String password;
    String type;
    String description;
    String email;
    String tel;
    Long id_location;
}
