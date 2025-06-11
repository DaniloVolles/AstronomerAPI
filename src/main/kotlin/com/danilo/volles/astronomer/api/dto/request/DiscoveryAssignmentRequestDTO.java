package com.danilo.volles.astronomer.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record DiscoveryAssignmentRequestDTO(

        @NotEmpty(message = "celestialObjectNameList field must not be empty")
        List<@NotBlank String> celestialObjectNameList
) {
}
