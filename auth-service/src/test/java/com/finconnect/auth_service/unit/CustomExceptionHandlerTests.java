package com.finconnect.auth_service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import com.finconnect.auth_service.exception.CustomExceptionHandler;
import com.finconnect.auth_service.exception.ExceptionResponse;
import com.finconnect.auth_service.exception.exceptions.DuplicateUserException;
import com.finconnect.auth_service.exception.exceptions.ExpiredTokenException;
import com.finconnect.auth_service.exception.exceptions.IntegrationException;
import com.finconnect.auth_service.exception.exceptions.InvalidCredentialsException;
import com.finconnect.auth_service.exception.exceptions.InvalidDataException;

@ExtendWith(MockitoExtension.class)
public class CustomExceptionHandlerTests {

    private CustomExceptionHandler handler;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        handler = new CustomExceptionHandler();
    }

    @Test
    void shouldHandleDuplicateUserException() {
        String errorMessage = "User already exists";
        String description = "uri=/auth/signup";
        DuplicateUserException ex = new DuplicateUserException(errorMessage);
        when(webRequest.getDescription(false)).thenReturn(description);

        ResponseEntity<ExceptionResponse> response = handler.handleDuplicateUserExEntity(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().message());
        assertEquals(description, response.getBody().details());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void shouldHandleInvalidCredentialsException() {
        String errorMessage = "Invalid username or password";
        InvalidCredentialsException ex = new InvalidCredentialsException(errorMessage);
        when(webRequest.getDescription(false)).thenReturn("any-details");

        ResponseEntity<ExceptionResponse> response = handler.handleInvalidCredentialsException(ex, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().message());
    }

    @Test
    void shouldHandleExpiredTokenException() {
        String errorMessage = "Token has expired";
        ExpiredTokenException ex = new ExpiredTokenException(errorMessage);
        when(webRequest.getDescription(false)).thenReturn("uri=/auth/me");

        ResponseEntity<ExceptionResponse> response = handler.handleExpiredTokenException(ex, webRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().message());
    }

    @Test
    void shouldHandleIntegrationException() {
        String errorMessage = "Error communicating with another service";
        IntegrationException ex = new IntegrationException(errorMessage);
        when(webRequest.getDescription(false)).thenReturn("internal-service-error");

        ResponseEntity<ExceptionResponse> response = handler.handleIntegrationException(ex, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().message());
    }

    @Test
    void shouldHandleInvalidDataException() {
        String errorMessage = "Invalid input data";
        InvalidDataException ex = new InvalidDataException(errorMessage);
        when(webRequest.getDescription(false)).thenReturn("validation-error");

        ResponseEntity<ExceptionResponse> response = handler.handleInvalidDataException(ex, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().message());
    }

    @Test
    void shouldHandleGeneralException() {
        String errorMessage = "Unexpected error";
        Exception ex = new RuntimeException(errorMessage);
        when(webRequest.getDescription(false)).thenReturn("general-details");

        ResponseEntity<ExceptionResponse> response = handler.handleInternalServerError(ex, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }
}