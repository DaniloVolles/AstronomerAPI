package com.danilo.volles.astronomer.api.dto.response;

import com.danilo.volles.astronomer.api.model.CelestialObjectType;

public record CelestialObjectResponseDTO(
        String name,
        CelestialObjectType type
) {

    public CelestialObjectResponseDTO(com.danilo.volles.celestial.objects.api.wsdl.CelestialObject celestialObjectFromClient) {
        this(
                celestialObjectFromClient.getName(),
                CelestialObjectType.fromClientValue(celestialObjectFromClient.getCelestialObjectType())
        );
    }
}
