package com.example.myEcommerceAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.myEcommerceAPI.exception.EntityNotFoundException;
import com.example.myEcommerceAPI.exception.ErrorResponse;
import com.example.myEcommerceAPI.exception.GradeNotFoundException;
import com.example.myEcommerceAPI.exception.StudentNotEnrolledException;


@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, GradeNotFoundException.class, StudentNotEnrolledException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(EmptyResultDataAccessException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList("Cannot delete non-existing resource"));  
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = ex.getMessage().toLowerCase();
        System.err.println(errorMessage);
        String responseMessage = "Data integrity violation: We cannot process your request.";
System.err.println(errorMessage);

        ErrorResponse error = new ErrorResponse(Collections.singletonList(responseMessage));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
           List<String> errors = new ArrayList<>();
           ex.getBindingResult().getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage()));
          return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
}
