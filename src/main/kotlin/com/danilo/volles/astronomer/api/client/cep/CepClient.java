package com.danilo.volles.astronomer.api.client.cep;

import com.danilo.volles.astronomer.api.client.cep.dto.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viaCepClient", url = "https://viacep.com.br/ws")
public interface CepClient {

    @GetMapping("/{cep}/json/")
    ViaCepResponse searchAddress(@PathVariable("cep") String cep);
}
