package com.finconnect.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExpiredTokenException extends RuntimeException {

    public ExpiredTokenException() {
        super("Token expired");
    }

    public ExpiredTokenException(String message) {
        super(message);
    }
}
