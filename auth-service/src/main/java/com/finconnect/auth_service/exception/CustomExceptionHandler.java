package com.finconnect.auth_service.exception;

import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.finconnect.auth_service.exception.exceptions.DuplicateUserException;
import com.finconnect.auth_service.exception.exceptions.ExpiredTokenException;
import com.finconnect.auth_service.exception.exceptions.IntegrationException;
import com.finconnect.auth_service.exception.exceptions.InvalidCredentialsException;
import com.finconnect.auth_service.exception.exceptions.InvalidDataException;


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

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateUserExEntity(DuplicateUserException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
        );


        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredTokenException(ExpiredTokenException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
        );


        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ExceptionResponse> handleIntegrationException(IntegrationException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
        );


        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCredentialsException(InvalidCredentialsException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
        );


        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidDataException(InvalidDataException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
        );


        return ResponseEntity.badRequest().body(response);
    }
}
