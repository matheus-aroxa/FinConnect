package com.finconnect.auth_service.controller.docs;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import com.finconnect.auth_service.dto.AccountResponse;
import com.finconnect.auth_service.dto.EmailFromCpfRequest;
import com.finconnect.auth_service.dto.EmailFromCpfResponse;
import com.finconnect.auth_service.dto.SignInRequest;
import com.finconnect.auth_service.dto.SignUpRequest;
import com.finconnect.auth_service.dto.UserInfoFromJwtResponse;
import com.finconnect.auth_service.dto.UserInfoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface AuthControllerDocs {

    @Operation(summary = "Authenticates an user", description = "Authenticates an user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public String authenticateUser(@Valid @RequestBody SignInRequest request);
    //---------------------------//----------------------------//-----------------------------//----------------//
    @Operation(summary = "Registers an user", description = "Registers an user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "409", description = "Duplicated user")
    })
    public ResponseEntity<AccountResponse> registerUser(@Valid @RequestBody SignUpRequest request) throws BadRequestException;
    //---------------------------//----------------------------//-----------------------------//----------------//
    @Operation(summary = "Extract the information from the jwt token", description = "Extract the username, issuedAt and expiryDate from jwt token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Information extracted successfully"),
        @ApiResponse(responseCode = "400", description = "Not a valid jwt")
    })
    public ResponseEntity<UserInfoFromJwtResponse> me(@RequestBody UserInfoRequest request);
    //---------------------------//----------------------------//-----------------------------//----------------//
    @Operation(summary = "Get the user's email from cpf", description = "used internally in the system for communication between microservices")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Got email successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<EmailFromCpfResponse> findEmailFromCpf(@RequestBody EmailFromCpfRequest request);
}
