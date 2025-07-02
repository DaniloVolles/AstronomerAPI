package com.danilo.volles.astronomer.api.service;

import com.danilo.volles.astronomer.api.client.cep.dto.ViaCepResponse;

public interface AddressService {
    ViaCepResponse getAddress(String cep);
}
