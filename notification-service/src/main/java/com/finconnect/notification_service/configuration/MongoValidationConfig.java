package com.finconnect.notification_service.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoValidationConfig {
    @Bean
    public CommandLineRunner validateMongo(MongoTemplate mongoTemplate) {
        return args -> {
            mongoTemplate.executeCommand("{ ping: 1 }");
        };
    }
}
