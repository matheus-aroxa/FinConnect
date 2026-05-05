package com.finconnect.transaction_service.exception;

import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleInternalServerError(Exception ex, WebRequest request) {
        var exception = new ExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

        return ResponseEntity.internalServerError().body(exception);
    }
}
