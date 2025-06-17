package com.danilo.volles.astronomer.api.dto.response;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public record ErrorResponse<String>(
        Instant instant,
        List<String> errors
) {
    public ErrorResponse(List<String> errors) {
        this(Instant.now(), errors);
    }

    public ErrorResponse(String error) {
        this(Instant.now(), Collections.singletonList(error));
    }
}
