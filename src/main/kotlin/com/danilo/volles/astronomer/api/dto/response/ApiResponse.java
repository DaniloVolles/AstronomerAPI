package com.danilo.volles.astronomer.api.dto.response;

import java.time.Instant;

public record ApiResponse<T>(Instant timestamp, T data){

    public ApiResponse(T data) {
        this(Instant.now(), data);
    }
}
