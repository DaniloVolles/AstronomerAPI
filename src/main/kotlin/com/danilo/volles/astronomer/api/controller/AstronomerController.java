package com.danilo.volles.astronomer.api.controller;

import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.request.DiscoveryAssignmentRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.ApiResponse;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
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
@RestController
@RequestMapping("/astronomer")
public class AstronomerController implements AstronomerEndpoints {

    private final AstronomerService astronomerService;

    public AstronomerController(AstronomerService astronomerService)   {
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
    @PostMapping("/{id}/assign/discoveries")
    public ResponseEntity<ApiResponse<List<CelestialObjectResponseDTO>>> assignDiscoveriesToAstronomer(
            @PathVariable UUID id,
            @Valid @RequestBody DiscoveryAssignmentRequestDTO attibuteRequestDTO
    ) {
        log.info("[POST] /attributeCelestialObjectDiscovery :: Endpoint accessed: attibuteCelestialObjectDiscovery");
        var celestialObjects = astronomerService.assignDiscoveriesToAstronomer(id, attibuteRequestDTO);
        log.info("[POST] /attributeCelestialObjectDiscovery :: Discoveries attributed successfully to id {}", id);
        return ResponseEntity
                .ok(new ApiResponse<>(celestialObjects));
    }

    @Override
    @GetMapping("/{id}/discoveries")
    public ResponseEntity<ApiResponse<List<CelestialObjectResponseDTO>>> getDiscoveriesByAstronomerId(@PathVariable UUID id) {
        log.info("[GET] /astronomer/discoveries :: Endpoint accessed: getDiscoveriesByAstronomerName");
        var celestialObjects = astronomerService.getDiscoveriesByAstronomerId(id);
        log.info("[GET] /astronomer/discoveries :: Discoveries returned successfully with astronomer id {}", id);
        return ResponseEntity
                .ok(new ApiResponse<>(celestialObjects));
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
    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<ApiResponse<AstronomerResponseDTO>> deactivateAstronomerById(@PathVariable UUID id) {
        log.info("[PATCH] /astronomer/inactive/{} :: Endpoint accessed: inactivateAstronomerById", id);
        var astronomer = astronomerService.deactivateAstronomerById(id);
        log.info("[PATCH] /astronomer/inactive/{} :: Astronomer inactivated successfully", id);
        return ResponseEntity
                .ok(new ApiResponse<>(astronomer));
    }
}
