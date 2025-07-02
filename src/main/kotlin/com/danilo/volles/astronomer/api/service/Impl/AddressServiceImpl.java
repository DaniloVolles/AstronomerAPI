package com.danilo.volles.astronomer.api.service.Impl;

import com.danilo.volles.astronomer.api.client.cep.CepClient;
import com.danilo.volles.astronomer.api.client.cep.dto.ViaCepResponse;
import com.danilo.volles.astronomer.api.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    private final CepClient cepClient;

    public AddressServiceImpl(CepClient cepClient) {
        this.cepClient = cepClient;
    }

    @Override
    public ViaCepResponse getAddress(String cep) {

        log.info("Getting address for CEP: {}", cep);

        return Optional.ofNullable(cepClient.searchAddress(cep))
                .orElseThrow(() -> new RuntimeException("No address found for this CEP code: " + cep));
    }
}
