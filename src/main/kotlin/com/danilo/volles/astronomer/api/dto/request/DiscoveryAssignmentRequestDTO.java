package com.danilo.volles.astronomer.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "Request object for assigning a list of celestial discoveries to a specific astronomer.")
public record DiscoveryAssignmentRequestDTO(

        @Schema(
                description = "List of celestial object names to be assigned to the astronomer",
                example = "[\"Alpha Centauri\", \"Betelgeuse\"]"
        )
        @NotEmpty(message = "celestialObjectNameList field must not be empty")
        List<@NotBlank String> celestialObjectNameList
) {
}
