package com.danilo.volles.astronomer.api.controller;

import com.danilo.volles.astronomer.api.dto.request.AstronomerNameDTO;
import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.request.AttributeRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.ApiResponse;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.AttributeResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import com.danilo.volles.astronomer.api.service.AstronomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping("/astronomer")
public class AstronomerController implements AstronomerEndpoints {

    private final AstronomerService astronomerService;

    public AstronomerController(AstronomerService astronomerService) {
        this.astronomerService = astronomerService;
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<AstronomerResponseDTO>> saveAstronomer(@Valid @RequestBody AstronomerRequestDTO astronomerDTO) {
        log.info("Astronomer endpoint accessed: saveAstronomer");
        var astronomer = astronomerService.saveAstronomer(astronomerDTO);
        var location = URI.create("/astronomers/" + astronomer.id());
        log.info("Astronomer saved successfully");
        return ResponseEntity
                .created(location)
                .body(new ApiResponse<>(astronomer));
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<AstronomerResponseDTO>>> getAllAstronomers() {
        log.info("Astronomer endpoint accessed: getAllAstronomers");
        var astronomer = astronomerService.getAstronomers();
        log.info("Astronomer list returned successfully");
        return ResponseEntity
                .ok(new ApiResponse<>(astronomer));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AstronomerResponseDTO>> getAstronomerById(@PathVariable UUID id) {
        log.info("Astronomer endpoint accessed: getAstronomerById");
        var astronomer = astronomerService.getAstronomerById(id);
        log.info("Astronomer returned successfully with id {}", id);
        return ResponseEntity
                .ok(new ApiResponse<>(astronomer));
    }

    @Override
    @GetMapping("/city")
    public ResponseEntity<ApiResponse<List<AstronomerResponseDTO>>> getAstronomersByCity(
            @RequestParam @NotBlank String city
    ) {
        log.info("Astronomer endpoint accessed: getAstronomersByCity");
        var astronomers = astronomerService.getAstronomerByCity(city);
        log.info("Astronomers returned successfully with city name {}", city);
        return ResponseEntity
                .ok(new ApiResponse<>(astronomers));
    }

    @Override
    public ResponseEntity<ApiResponse<AttributeResponseDTO>> attibuteCelestialObjectDiscovery(AttributeRequestDTO attibuteRequestDTO) {
        return null;
    }

    @Override
    @GetMapping("/discoveries")
    public ResponseEntity<ApiResponse<List<CelestialObjectResponseDTO>>> getDiscoveriesByAstronomerName(@RequestBody AstronomerNameDTO astronomer) {
        return null;
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AstronomerResponseDTO>> updateAstronomeById(@PathVariable UUID id) {
        return null;
    }

    @Override
    @PatchMapping("/inactive/{id}")
    public ResponseEntity<ApiResponse<AstronomerResponseDTO>> inactivateAstronomerById(@PathVariable UUID id) {
        return null;
    }
}
