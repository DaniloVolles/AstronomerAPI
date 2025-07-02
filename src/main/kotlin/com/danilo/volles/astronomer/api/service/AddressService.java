package com.danilo.volles.astronomer.api.service;

import com.danilo.volles.astronomer.api.client.cep.dto.ViaCepResponse;
import org.springframework.stereotype.Service;

/**
 * Service interface for retrieving address information from external postal code (CEP) providers.
 * <p>
 * This service acts as a wrapper around the ViaCEP client and provides
 * address resolution based on Brazilian postal codes (CEP).
 * </p>
 *
 * <p>
 * It is typically used to enrich astronomer data during registration or updates,
 * ensuring accurate and standardized address details.
 * </p>
 */
@Service
public interface AddressService {

    /**
     * Retrieves address information based on a Brazilian postal code (CEP).
     * @param cep the postal code to be searched
     * @return the address information returned from the external service
     */
    ViaCepResponse getAddress(String cep);
}
