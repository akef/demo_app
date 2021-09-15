package com.demo.jumia.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NoDataException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                ex.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ExceptionResponse> handleAcceptableException(InvalidException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(new Date(),
                HttpStatus.NOT_ACCEPTABLE.value(),
                HttpStatus.NOT_ACCEPTABLE.name(),
                ex.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleUnknownException(Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "INTERNAL_ERROR");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
