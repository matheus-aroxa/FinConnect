package com.finconnect.auth_service.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserNotFoundByEmailException extends RuntimeException {
    
    public UserNotFoundByEmailException(String message) {
        super(message);
    }

    public UserNotFoundByEmailException() {
        super("User not found for this email");
    }
}
