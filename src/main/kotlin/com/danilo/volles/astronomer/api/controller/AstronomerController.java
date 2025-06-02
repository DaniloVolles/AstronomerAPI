package com.danilo.volles.astronomer.api.controller;

import com.danilo.volles.astronomer.api.dto.request.AstronomerNameDTO;
import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.request.CityRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/astronomer")
public class AstronomerController implements AstronomerEndpoints {

    @Override
    @PostMapping
    public ResponseEntity<AstronomerResponseDTO> saveAstronomer(@RequestBody AstronomerRequestDTO astronomerDTO) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<AstronomerResponseDTO> getAllAstronomers() {
        return null;
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
