package com.danilo.volles.astronomer.api.model;

import com.danilo.volles.astronomer.api.exception.InvalidCelestialObjectTypeValueException;

import java.util.Arrays;

public enum CelestialObjectType {
    STAR,
    PLANET,
    MOON,
    DWARF_PLANET,
    ASTEROID,
    COMET,
    OTHER;

    public static CelestialObjectType fromClientValue(
            com.danilo.volles.celestial.objects.api.wsdl.CelestialObjectType clientType) {
        return Arrays.stream(CelestialObjectType.values())
                .filter(type -> type.name().equalsIgnoreCase(clientType.value()))
                .findFirst()
                .orElseThrow(InvalidCelestialObjectTypeValueException::new);
    }
}