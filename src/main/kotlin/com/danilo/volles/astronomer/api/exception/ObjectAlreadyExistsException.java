package com.danilo.volles.astronomer.api.exception;

import com.danilo.volles.astronomer.api.util.Constants;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException() {
        super(Constants.Error.OBJECT_ALREADY_EXISTS);
    }
}
