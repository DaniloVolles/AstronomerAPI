package com.danilo.volles.astronomer.api.service;

import com.danilo.volles.astronomer.api.client.cep.CepClient;
import com.danilo.volles.astronomer.api.client.cep.responseDto.ViaCepResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    private static final Logger log = LoggerFactory.getLogger(AddressService.class);
    private final CepClient cepClient;

    public AddressService(CepClient cepClient) {
        this.cepClient = cepClient;
    }

    public ViaCepResponse getAddress(String cep) {

        System.out.println(cep);

        log.info("Getting address for CEP: {}", cep);

        // TODO - passar para Inglês
        return Optional.ofNullable(cepClient.searchAddress(cep))
                .orElseThrow(() -> new RuntimeException("Nenhum endereço encontrado com esse cep: " + cep));
    }
}
