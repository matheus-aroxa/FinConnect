package com.finconnect.transaction_service.exception;

import com.finconnect.transaction_service.exception.exceptions.AccountNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new AccountNotFoundException();
            default -> defaultErrorDecoder.decode(methodKey, response);
        };
    }
    
}
