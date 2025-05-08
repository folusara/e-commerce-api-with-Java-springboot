package com.example.myEcommerceAPI.exception;

import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException cause) {
            String fieldName = cause.getPath().get(0).getFieldName();
            String invalidValue = cause.getValue().toString();
            String allowedValues = "SUCCESS, FAILED, PENDING";
            return ResponseEntity.badRequest().body(
                Map.of(
                    "message", "Invalid value '" + invalidValue + "' for field '" + fieldName + "'. Allowed values: " + allowedValues
                )
            );
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                .body(Map.of("message", "Malformed JSON or invalid request"));
    }
}
