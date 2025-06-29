package com.danilo.volles.astronomer.api.dto.response;

import java.time.Instant;

public record ApiResponseDTO<T>(Instant timestamp, T data){

    public ApiResponseDTO(T data) {
        this(Instant.now(), data);
    }
}
