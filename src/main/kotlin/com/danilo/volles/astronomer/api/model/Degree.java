package com.danilo.volles.astronomer.api.model;

import com.danilo.volles.astronomer.api.exception.InvalidDegreeValueException;

import java.util.Arrays;

public enum Degree {
    BACHELOR,
    MASTER,
    DOCTOR;

    public static Degree fromString(final String value) {
        return Arrays.stream(Degree.values())
                .filter(degree -> degree.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(InvalidDegreeValueException::new);
    }
}


