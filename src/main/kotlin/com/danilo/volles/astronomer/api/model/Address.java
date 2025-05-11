package com.danilo.volles.astronomer.api.model;

import lombok.Data;

/**
 * This class was written in english, but it refers to a brazillian address context
 * named as CEP (Cidade, Estado, País). Like a ZIP Code system.
 */
@Data
public class Address {
    private String street;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
}
