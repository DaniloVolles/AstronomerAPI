package com.danilo.volles.astronomer.api.controller;

import com.danilo.volles.astronomer.api.client.celestialObjects.CelestialObjectsClient;
import com.danilo.volles.astronomer.api.service.AddressService;
import com.danilo.volles.celestial.objects.api.wsdl.GetAllCelestialObjectsResponse;
import com.danilo.volles.celestial.objects.api.wsdl.GetCelestialObjectByNameResponse;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/objectByName")
    public GetCelestialObjectByNameResponse getCelestialObjectByName(@RequestParam String name) {
        return celestialObjectsClient.getCelestialObjectByName(name);
    }
}
