package com.danilo.volles.astronomer.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Schema(description = "Standard API error response wrapper")
public record ErrorResponse<String>(

        @Schema(description = "Timestamp when the response was generated", example = "2025-06-29T17:32:59.196547327Z")
        Instant instant,

        @Schema(description = "List of errors thrown by the API", example = "[ Object already found in this database ]")
        List<String> errors
) {
    public ErrorResponse(List<String> errors) {
        this(Instant.now(), errors);
    }

    public ErrorResponse(String error) {
        this(Instant.now(), Collections.singletonList(error));
    }
}
