package com.danilo.volles.astronomer.api.dto.request;

import java.util.UUID;

public record AttributeRequestDTO(
        UUID astronomerId,
        Integer celestialObjectId
) {
}
