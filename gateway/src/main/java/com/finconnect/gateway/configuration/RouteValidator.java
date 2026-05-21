package com.finconnect.gateway.configuration;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
    
    public static final List<String> openApiEndpoints = List.of(
        "/api/auth/signin",
        "/api/auth/signup",
        "/api/accounts/v3/api-docs",
        "/api/transactions/v3/api-docs",
        "/api/auth/v3/api-docs",
        "/swagger-ui",
        "/swagger-ui/**"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
