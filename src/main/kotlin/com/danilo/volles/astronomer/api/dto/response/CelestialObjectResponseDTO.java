package com.danilo.volles.astronomer.api.dto.response;

import com.danilo.volles.astronomer.api.model.CelestialObjectType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO that represents a celestial object associated with an astronomer")
public record CelestialObjectResponseDTO(

        @Schema(description = "Name of the celestial object", example = "Alpha Centauri")
        String name,

        @Schema(description = "Type of the celestial object", example = "STAR", implementation = CelestialObjectType.class)
        CelestialObjectType type
) {

    public CelestialObjectResponseDTO(com.danilo.volles.celestial.objects.api.wsdl.CelestialObject celestialObjectFromClient) {
        this(
                celestialObjectFromClient.getName(),
                CelestialObjectType.fromClientValue(celestialObjectFromClient.getCelestialObjectType())
        );
    }
}
