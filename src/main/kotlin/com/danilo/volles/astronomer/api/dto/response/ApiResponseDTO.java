package com.danilo.volles.astronomer.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Standard API response wrapper")
public record ApiResponseDTO<T>(

        @Schema(description = "Timestamp when the response was generated", example = "2025-06-29T17:32:59.196547327Z")
        Instant timestamp,

        @Schema(description = "Actual data returned by the API", example = "« Generic Data »")
        T data
){

    public ApiResponseDTO(T data) {
        this(Instant.now(), data);
    }
}
