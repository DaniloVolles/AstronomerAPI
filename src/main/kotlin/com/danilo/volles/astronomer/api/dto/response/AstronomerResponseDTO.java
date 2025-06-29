package com.danilo.volles.astronomer.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO that represents an astronomer returned by the API")
public record AstronomerResponseDTO(

        @Schema(description = "Unique identifier of the astronomer", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
        UUID id,

        @Schema(description = "Full name of the astronomer", example = "Carl Sagan")
        String fullName,

        @Schema(description = "Email address of the astronomer", example = "carl.sagan@nasa.gov")
        String email,

        @Schema(description = "Name of the institution or observatory", example = "NASA Jet Propulsion Laboratory")
        String institution,

        @Schema(description = "Indicates whether the astronomer is active", example = "true")
        Boolean isActive
) {
}
