package com.danilo.volles.astronomer.api.client.cep;

import com.danilo.volles.astronomer.api.client.cep.dto.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client interface for accessing the public ViaCEP REST API.
 *
 * <p>
 * This interface provides methods to retrieve address information based on
 * Brazilian postal codes (CEP). It sends HTTP requests to the external ViaCEP service
 * located at {@code https://viacep.com.br/ws}.
 * </p>
 *
 * <p>
 * The service returns data in JSON format and does not require authentication.
 * </p>
 */
@FeignClient(name = "viaCepClient", url = "https://viacep.com.br/ws")
public interface CepClient {

    /**
     * Retrieves address information for the given Brazilian postal code (CEP).
     *
     * <p>
     * Sends a GET request to the ViaCEP API and parses the JSON response into a {@link ViaCepResponse}.
     * </p>
     *
     * @param cep the postal code to be searched (e.g. "01001-000")
     * @return the address details corresponding to the provided CEP
     */
    @GetMapping("/{cep}/json/")
    ViaCepResponse searchAddress(@PathVariable("cep") String cep);
}
