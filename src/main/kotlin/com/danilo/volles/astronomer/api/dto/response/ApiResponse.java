package com.danilo.volles.astronomer.api.dto.response;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ApiResponse<T> {
    private final Instant timestamp = Instant.now();
    private final T data;

    public ApiResponse(T data) {
        this.data = data;
    }
}
