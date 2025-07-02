package com.danilo.volles.astronomer.api.service.Impl;

import com.danilo.volles.astronomer.api.client.cep.CepClient;
import com.danilo.volles.astronomer.api.client.cep.dto.ViaCepResponse;
import com.danilo.volles.astronomer.api.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Concrete implementation of {@link AddressService}, using {@code CepClient}
 * to fetch address data from the ViaCEP external service.
 *
 * <p>
 * This class acts as a simple adapter, isolating HTTP client logic from the rest of the application.
 * </p>
 *
 * <p>
 * Failures in this layer are typically wrapped or propagated as runtime exceptions
 * to be handled by the global exception handler.
 * </p>
 */
@Slf4j
@Component
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
