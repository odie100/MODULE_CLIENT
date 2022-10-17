package com.akata.clientservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationModel {
    private String to;
    private String from;
    private String subject;
    private String text;
    private String template;
    private String name;
    private Map<String, Object> properties;
}
