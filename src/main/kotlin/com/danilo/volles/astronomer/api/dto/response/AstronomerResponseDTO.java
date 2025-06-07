package com.danilo.volles.astronomer.api.dto.response;

import java.util.UUID;

public record AstronomerResponseDTO(
        UUID id,
        String fullName,
        String email,
        String institution
) {
}
