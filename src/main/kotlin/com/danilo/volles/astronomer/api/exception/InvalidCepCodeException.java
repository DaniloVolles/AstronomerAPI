package com.danilo.volles.astronomer.api.exception;

import com.danilo.volles.astronomer.api.util.Constants;

public class InvalidCepCodeException extends RuntimeException {
    public InvalidCepCodeException() {
        super(Constants.Error.INVALID_CEP);
    }
}
