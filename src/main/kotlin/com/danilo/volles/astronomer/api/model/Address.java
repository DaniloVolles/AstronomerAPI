package com.danilo.volles.astronomer.api.model;

import com.danilo.volles.astronomer.api.client.cep.responseDto.ViaCepResponse;
import com.danilo.volles.astronomer.api.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class was written in english, but it refers to a brazillian address context
 * named as CEP (Cidade, Estado, País). Like a ZIP Code system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;

    public Address(ViaCepResponse viaCepResponse) {
        this.street = viaCepResponse.logradouro();
        this.complement = viaCepResponse.complemento();
        this.neighborhood = viaCepResponse.bairro();
        this.city = viaCepResponse.localidade();
        this.state = viaCepResponse.uf();
        this.country = Constants.Country.BR;
    }
}
