package com.danilo.volles.astronomer.api.controller;

import com.danilo.volles.astronomer.api.client.celestialObjects.CelestialObjectsClient;
import com.danilo.volles.astronomer.api.service.AddressService;
import com.danilo.volles.celestial.objects.api.wsdl.GetAllCelestialObjectsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TestController {

    private final AddressService addressService;
    private final CelestialObjectsClient celestialObjectsClient;

    public TestController(AddressService addressService, CelestialObjectsClient celestialObjectsClient) {
        this.addressService = addressService;
        this.celestialObjectsClient = celestialObjectsClient;
    }

    @GetMapping("/{cep}")
    public String getAddress(@PathVariable String cep) {
        return addressService.getAddress(cep).toString();
    }

    @GetMapping("/allCelestialObjects")
    public GetAllCelestialObjectsResponse getAllCelestialObjects() {
        return celestialObjectsClient.getAllCelestialObjects();
    }
}
