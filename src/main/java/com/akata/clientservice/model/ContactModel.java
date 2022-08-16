package com.akata.clientservice.model;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class ContactModel {
    private String type;
    private String value;
}
