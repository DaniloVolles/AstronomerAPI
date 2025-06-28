package com.danilo.volles.astronomer.api.service;

import com.danilo.volles.astronomer.api.client.cep.CepClient;
import com.danilo.volles.astronomer.api.client.cep.responseDto.ViaCepResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AddressService {

    private final CepClient cepClient;

    public AddressService(CepClient cepClient) {
        this.cepClient = cepClient;
    }

    public ViaCepResponse getAddress(String cep) {

        log.info("Getting address for CEP: {}", cep);

        return Optional.ofNullable(cepClient.searchAddress(cep))
                .orElseThrow(() -> new RuntimeException("No address found for this CEP code: " + cep));
    }
}
