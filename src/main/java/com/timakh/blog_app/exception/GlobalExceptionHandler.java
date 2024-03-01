package com.timakh.blog_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchEmptyAddressException(EmptyAddressException e) {
        return new ResponseEntity<>(new ErrorResponse(new Date(), HttpStatus.NO_CONTENT.value(), e.getMessage()), HttpStatus.NO_CONTENT);
    }
}


