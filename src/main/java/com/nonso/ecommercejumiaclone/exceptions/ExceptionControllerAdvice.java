package com.nonso.ecommercejumiaclone.exceptions;

import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.payload.response.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = ProductDoesNotExistException.class)
    public ResponseEntity<?> handleProductDoesNotExistException(ProductDoesNotExistException ex) {
        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()), false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CustomNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomNotFoundException(CustomNotFoundException ex) {
        return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleCustomNotFoundException(Exception ex) {
        return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

}
