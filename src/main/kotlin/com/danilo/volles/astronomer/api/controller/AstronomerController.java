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
        log.info("[POST] /astronomer :: Endpoint accessed: saveAstronomer");
        var astronomer = astronomerService.saveAstronomer(astronomerDTO);
        var location = URI.create("/astronomers/" + astronomer.id());
        log.info("[POST] /astronomer :: Astronomer saved successfully {}", astronomer.id());
        return ResponseEntity
                .created(location)
                .body(new ApiResponse<>(astronomer));
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<AstronomerResponseDTO>>> getAllAstronomers() {
        log.info("[GET] /astronomer :: Endpoint accessed: getAllAstronomers");
        var astronomer = astronomerService.getAstronomers();
        log.info("[GET] /astronomer :: Astronomer list returned successfully");
        return ResponseEntity
                .ok(new ApiResponse<>(astronomer));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AstronomerResponseDTO>> getAstronomerById(@PathVariable UUID id) {
        log.info("[GET] /astronomer/{} :: Endpoint accessed: getAstronomerById", id);
        var astronomer = astronomerService.getAstronomerById(id);
        log.info("[GET] /astronomer/{} :: Astronomer returned successfully", id);
        return ResponseEntity
                .ok(new ApiResponse<>(astronomer));
    }

    @Override
    @GetMapping("/city")
    public ResponseEntity<ApiResponse<List<AstronomerResponseDTO>>> getAstronomersByCity(
            @RequestParam @NotBlank String city
    ) {
        log.info("[GET] /astronomer/city :: Endpoint accessed: getAstronomersByCity");
        var astronomers = astronomerService.getAstronomerByCity(city);
        log.info("[GET] /astronomer/city :: Astronomers returned successfully with city name {}", city);
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
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AstronomerResponseDTO>> updateAstronomerById(
            @PathVariable UUID id,
            @Valid @RequestBody AstronomerRequestDTO requestDTO
    ) {
        log.info("[PUT] /astronomer/{} :: Endpoint accessed: updateAstronomeById", id);
        var astronomer = astronomerService.updateAstronomerById(id, requestDTO);
        log.info("[PUT] /astronomer/{} :: Astronomer updated successfully", id);
        return ResponseEntity
                .ok(new ApiResponse<>(astronomer));
    }

    @Override
    @PatchMapping("/inactive/{id}")
    public ResponseEntity<ApiResponse<AstronomerResponseDTO>> inactivateAstronomerById(@PathVariable UUID id) {
        log.info("[PATCH] /astronomer/inactive/{} :: Endpoint accessed: inactivateAstronomerById", id);
        var astronomer = astronomerService.inactivateAstronomerById(id);
        log.info("[PATCH] /astronomer/inactive/{} :: Astronomer inactivated successfully", id);
        return ResponseEntity
                .ok(new ApiResponse<>(astronomer));
    }
}
