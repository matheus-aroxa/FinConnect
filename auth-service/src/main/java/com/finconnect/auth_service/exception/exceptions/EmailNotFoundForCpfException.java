package com.finconnect.auth_service.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailNotFoundForCpfException extends RuntimeException{
    
    public EmailNotFoundForCpfException() {
        super("No email for this cpf");
    }

    public EmailNotFoundForCpfException(String message) {
        super(message);
    }
}
