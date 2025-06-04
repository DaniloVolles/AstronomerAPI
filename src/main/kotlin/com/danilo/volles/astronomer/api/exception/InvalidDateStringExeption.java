package com.danilo.volles.astronomer.api.exception;

import com.danilo.volles.astronomer.api.util.Constants;

public class InvalidDateStringExeption extends RuntimeException {
    public InvalidDateStringExeption() {
        super(Constants.Error.INVALID_DATE_STRING);
    }
}
