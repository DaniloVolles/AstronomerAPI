package com.danilo.volles.astronomer.api.exception;

import com.danilo.volles.astronomer.api.util.Constants;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException() {
        super(Constants.Error.OBJECT_NOT_FOUND);
    }
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
