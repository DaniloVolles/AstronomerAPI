package com.danilo.volles.astronomer.api.exception;

import com.danilo.volles.astronomer.api.util.Constants;

public class InvalidDegreeValueException extends RuntimeException {
    public InvalidDegreeValueException() {
        super(Constants.Error.INVALID_DEGREE);
    }
}
