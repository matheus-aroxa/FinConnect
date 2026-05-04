package com.finconnect.account_service.exception;

import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.finconnect.account_service.exception.exceptions.InsufficientBalanceException;

@RestControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleInternalServerError(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
        );


        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ExceptionResponse> handleInsufficientBalanceException(InsufficientBalanceException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
        );


        return ResponseEntity.badRequest().body(response);
    }
}