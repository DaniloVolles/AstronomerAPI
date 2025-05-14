package com.danilo.volles.astronomer.api.dto.response;

import com.danilo.volles.astronomer.api.model.CelestialObjectType;

public record CelestialObjectResponseDTO(
        String name,
        CelestialObjectType type
) {
}
