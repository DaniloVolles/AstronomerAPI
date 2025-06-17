package com.danilo.volles.astronomer.api.exception;

import com.danilo.volles.astronomer.api.util.Constants;

public class InvalidDateStringException extends RuntimeException {
    public InvalidDateStringException() {
        super(Constants.Error.INVALID_DATE_STRING);
    }
}
