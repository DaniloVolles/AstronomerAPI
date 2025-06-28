package com.danilo.volles.astronomer.api.exception.handler;

import com.danilo.volles.astronomer.api.dto.response.ApiResponse;
import com.danilo.volles.astronomer.api.dto.response.ErrorResponse;
import com.danilo.volles.astronomer.api.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = InvalidCelestialObjectTypeValueException.class)
    public ResponseEntity<ErrorResponse<String>> invalidCelestialObjectTypeValueExceptionHandler(InvalidCelestialObjectTypeValueException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse<>(ex.getMessage()));
    }

    @ExceptionHandler({
            InvalidDateStringException.class,
            InvalidDegreeValueException.class
    })
    public ResponseEntity<ErrorResponse<String>> invalidInputExceptionHandler(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(value = ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> objectNotFoundExceptionHandler(ObjectNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(value = ObjectAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<String>> objectAlreadyExistsExceptionHandler(ObjectAlreadyExistsException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<String>> handleValidation(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse<>(errorMessages));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleGenericException(Exception ex) {
        return ResponseEntity
                .internalServerError()
                .body(new ErrorResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }
}
