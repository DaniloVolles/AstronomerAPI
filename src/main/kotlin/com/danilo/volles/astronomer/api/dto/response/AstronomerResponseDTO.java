package com.danilo.volles.astronomer.api.dto.response;

public record AstronomerResponseDTO(
        String fullName,
        String email,
        String institution
) {
}
