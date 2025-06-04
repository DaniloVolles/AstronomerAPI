package com.danilo.volles.astronomer.api.service;

import com.danilo.volles.astronomer.api.client.cep.CepClient;
import com.danilo.volles.astronomer.api.client.cep.responseDto.ViaCepResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    private final CepClient cepClient;

    public AddressService(CepClient cepClient) {
        this.cepClient = cepClient;
    }

    public ViaCepResponse getAddress(String cep) {
        // TODO - passar para Inglês
        return Optional.ofNullable(cepClient.searchAddress(cep))
                .orElseThrow(() -> new RuntimeException("Nenhum endereço encontrado com esse cep: " + cep));
    }
}
