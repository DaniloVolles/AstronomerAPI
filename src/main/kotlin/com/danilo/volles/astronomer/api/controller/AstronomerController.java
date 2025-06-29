package com.danilo.volles.astronomer.api.controller;

import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.request.DiscoveryAssignmentRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.ApiResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import com.danilo.volles.astronomer.api.service.AstronomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Astronomers", description = "Astronomer related endpoints")
@Slf4j
@RestController
@RequestMapping("/astronomer")
public class AstronomerController{

    private final AstronomerService astronomerService;

    public AstronomerController(AstronomerService astronomerService)   {
        this.astronomerService = astronomerService;
    }

    @Operation(
            summary = "Create a new Astronomer",
            description = "Registers a new astronomer in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Astronomer successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO<AstronomerResponseDTO>> saveAstronomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload to create a new astronomer",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AstronomerRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Example Insert Astronomer",
                                    value = """
                                            {
                                                "fullName": "Galileu Galilei",
                                                "birthDate": "1564-02-15",
                                                "email": "galileu.galilei@pisa.universita.it",
                                                "phone": "(11)91234-5678",
                                                "degree": "doctor",
                                                "researchArea": "Astronomia",
                                                "institution": "Universidade de Pisa",
                                                "cep": "71020-022",
                                                "active": false
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody AstronomerRequestDTO astronomerDTO
    ) {
        log.info("[POST] /astronomer :: Endpoint accessed: saveAstronomer");
        var astronomer = astronomerService.saveAstronomer(astronomerDTO);
        var location = URI.create("/astronomers/" + astronomer.id());
        log.info("[POST] /astronomer :: Astronomer saved successfully {}", astronomer.id());
        return ResponseEntity
                .created(location)
                .body(new ApiResponseDTO<>(astronomer));
    }

    @Operation(
            summary = "Get all astronomers",
            description = "Returns a list with all registered astronomers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Astronomers retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<AstronomerResponseDTO>>> getAllAstronomers() {
        log.info("[GET] /astronomer :: Endpoint accessed: getAllAstronomers");
        var astronomer = astronomerService.getAstronomers();
        log.info("[GET] /astronomer :: Astronomer list returned successfully");
        return ResponseEntity
                .ok(new ApiResponseDTO<>(astronomer));
    }

    @Operation(
            summary = "Get astronomer by ID",
            description = "Fetches an astronomer by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Astronomer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Astronomer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid UUID format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AstronomerResponseDTO>> getAstronomerById(
            @Parameter(name = "id", description = "Unique identifier of the astronomer", required = true)
            @PathVariable UUID id
    ) {
        log.info("[GET] /astronomer/{} :: Endpoint accessed: getAstronomerById", id);
        var astronomer = astronomerService.getAstronomerById(id);
        log.info("[GET] /astronomer/{} :: Astronomer returned successfully", id);
        return ResponseEntity
                .ok(new ApiResponseDTO<>(astronomer));
    }

    @Operation(
            summary = "Get astronomers by city",
            description = "Returns all astronomers from a specific city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Astronomers retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "City name cannot be blank"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/city")
    public ResponseEntity<ApiResponseDTO<List<AstronomerResponseDTO>>> getAstronomersByCity(
            @Parameter(name = "city", description = "City of the astronomer", required = true)
            @RequestParam @NotBlank String city
    ) {
        log.info("[GET] /astronomer/city :: Endpoint accessed: getAstronomersByCity");
        var astronomers = astronomerService.getAstronomerByCity(city);
        log.info("[GET] /astronomer/city :: Astronomers returned successfully with city name {}", city);
        return ResponseEntity
                .ok(new ApiResponseDTO<>(astronomers));
    }

    @Operation(
            summary = "Assign discoveries to an astronomer",
            description = "Associates celestial discoveries to a specific astronomer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discoveries assigned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or UUID"),
            @ApiResponse(responseCode = "404", description = "Astronomer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{id}/assign/discoveries")
    public ResponseEntity<ApiResponseDTO<List<CelestialObjectResponseDTO>>> assignDiscoveriesToAstronomer(
            @Parameter(name = "id", description = "Unique identifier of the astronomer", required = true)
            @PathVariable UUID id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of celestial object IDs to assign to the astronomer",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiscoveryAssignmentRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Discovery Assignment Example",
                                    value = """
                                            {
                                                "celestialObjectNameList": [ "Halley", "Mars", "Pluto" ]
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody DiscoveryAssignmentRequestDTO attributeRequestDTO
    ) {
        log.info("[POST] /attributeCelestialObjectDiscovery :: Endpoint accessed: attributeCelestialObjectDiscovery");
        var celestialObjects = astronomerService.assignDiscoveriesToAstronomer(id, attributeRequestDTO);
        log.info("[POST] /attributeCelestialObjectDiscovery :: Discoveries attributed successfully to id {}", id);
        return ResponseEntity
                .ok(new ApiResponseDTO<>(celestialObjects));
    }

    @Operation(summary = "Get discoveries by astronomer ID", description = "Returns a list of celestial objects discovered by a specific astronomer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discoveries retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Astronomer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid UUID format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}/discoveries")
    public ResponseEntity<ApiResponseDTO<List<CelestialObjectResponseDTO>>> getDiscoveriesByAstronomerId(
            @Parameter(name = "id", description = "Unique identifier of the astronomer", required = true)
            @PathVariable UUID id
    ) {
        log.info("[GET] /astronomer/discoveries :: Endpoint accessed: getDiscoveriesByAstronomerName");
        var celestialObjects = astronomerService.getDiscoveriesByAstronomerId(id);
        log.info("[GET] /astronomer/discoveries :: Discoveries returned successfully with astronomer id {}", id);
        return ResponseEntity
                .ok(new ApiResponseDTO<>(celestialObjects));
    }

    @Operation(summary = "Update an astronomer by ID", description = "Updates all fields of an existing astronomer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Astronomer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Astronomer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AstronomerResponseDTO>> updateAstronomerById(
            @Parameter(name = "id", description = "Unique identifier of the astronomer", required = true)
            @PathVariable UUID id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload to update all fields of the astronomer",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AstronomerRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Example Update Astronomer",
                                    value = """
                                            {
                                                "fullName": "Galileu Galilei",
                                                "birthDate": "1564-02-15",
                                                "email": "galileu.galilei@pisa.universita.it",
                                                "phone": "(11)91234-5678",
                                                "degree": "doctor",
                                                "researchArea": "Astronomia",
                                                "institution": "Universidade de Pisa",
                                                "cep": "71020-022",
                                                "active": false
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody AstronomerRequestDTO requestDTO
    ) {
        log.info("[PUT] /astronomer/{} :: Endpoint accessed: updateAstronomeById", id);
        var astronomer = astronomerService.updateAstronomerById(id, requestDTO);
        log.info("[PUT] /astronomer/{} :: Astronomer updated successfully", id);
        return ResponseEntity
                .ok(new ApiResponseDTO<>(astronomer));
    }

    @Operation(summary = "Deactivate an astronomer by ID", description = "Marks the astronomer as inactive without deleting it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Astronomer deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Astronomer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid UUID format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<ApiResponseDTO<AstronomerResponseDTO>> deactivateAstronomerById(
            @Parameter(name = "id", description = "Unique identifier of the astronomer", required = true)
            @PathVariable UUID id
    ) {
        log.info("[PATCH] /astronomer/inactive/{} :: Endpoint accessed: inactivateAstronomerById", id);
        var astronomer = astronomerService.deactivateAstronomerById(id);
        log.info("[PATCH] /astronomer/inactive/{} :: Astronomer inactivated successfully", id);
        return ResponseEntity
                .ok(new ApiResponseDTO<>(astronomer));
    }
}
