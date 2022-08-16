package com.akata.clientservice.model;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class ClientModel {
    String name;
    String country;
    String address;
    String town;
    String username;
    String type;
    String description;
    String email;
    String tel;
}
