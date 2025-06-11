package com.danilo.volles.astronomer.api.exception;

import com.danilo.volles.astronomer.api.util.Constants;

public class InvalidCelestialObjectTypeValueException extends RuntimeException {
    public InvalidCelestialObjectTypeValueException() {
        super(Constants.Error.INVALID_CELESTIAL_OBJECT_TYPE);
    }
}
