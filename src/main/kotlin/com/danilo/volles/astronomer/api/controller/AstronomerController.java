package com.danilo.volles.astronomer.api.controller;

import com.danilo.volles.astronomer.api.dto.request.AstronomerNameDTO;
import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.request.AttributeRequestDTO;
import com.danilo.volles.astronomer.api.dto.request.CityRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.AttributeResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import com.danilo.volles.astronomer.api.service.AddressService;
import com.danilo.volles.astronomer.api.service.AstronomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/astronomer")
public class AstronomerController implements AstronomerEndpoints {

    private AddressService addressService;
    private AstronomerService astronomerService;

    public AstronomerController(AddressService addressService, AstronomerService astronomerService) {
        this.addressService = addressService;
        this.astronomerService = astronomerService;
    }

    @Override
    @PostMapping
    public ResponseEntity<AstronomerResponseDTO> saveAstronomer(@Valid @RequestBody AstronomerRequestDTO astronomerDTO) {
        log.info("Astronomer endpoint accessed: saveAstronomer");
        var astronomer = astronomerService.saveAstronomer(astronomerDTO);
        log.info("Astronomer saved successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(astronomer);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AstronomerResponseDTO>> getAllAstronomers() {
        log.info("Astronomer endpoint accessed: getAllAstronomers");
        var astronomer = astronomerService.getAstronomers();
        log.info("Astronomer list returned successfully");
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(astronomer);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AstronomerResponseDTO> getAstronomerById(@PathVariable UUID id) {
        return null;
    }

    @Override
    @GetMapping("/city")
    public ResponseEntity<AstronomerResponseDTO> getAstronomerByCity(@RequestBody CityRequestDTO city) {
        return null;
    }

    @Override
    public ResponseEntity<AttributeResponseDTO> attibuteCelestialObjectDiscovery(AttributeRequestDTO attibuteRequestDTO) {
        return null;
    }

    @Override
    @GetMapping("/discoveries")
    public ResponseEntity<CelestialObjectResponseDTO> getDiscoveriesByAstronomerName(@RequestBody AstronomerNameDTO astronomer) {
        return null;
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<AstronomerResponseDTO> updateAstronomeById(@PathVariable UUID id) {
        return null;
    }

    @Override
    @PatchMapping("/inactive/{id}")
    public ResponseEntity<AstronomerResponseDTO> inactivateAstronomerById(@PathVariable UUID id) {
        return null;
    }
}
