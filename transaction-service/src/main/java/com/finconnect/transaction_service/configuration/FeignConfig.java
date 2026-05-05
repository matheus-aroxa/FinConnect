package com.finconnect.transaction_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.finconnect.transaction_service.exception.CustomErrorDecoder;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfig {
    
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
