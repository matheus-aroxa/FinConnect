package com.finconnect.auth_service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.finconnect.auth_service.dto.SignInRequest;
import com.finconnect.auth_service.dto.SignUpRequest;
import com.finconnect.auth_service.dto.UserInfoRequest;
import com.finconnect.auth_service.entity.Users;
import com.finconnect.auth_service.exception.exceptions.DuplicateUserException;
import com.finconnect.auth_service.feign.AccountClient;
import com.finconnect.auth_service.repository.UsersRepository;
import com.finconnect.auth_service.service.AuthService;
import com.finconnect.auth_service.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private AccountClient accountClient;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private AuthService authService;

    @Test
    public void shouldReturnValidTokenWhenAuthenticatingValidUser() {
        SignInRequest request = new SignInRequest("user@email.com", "password123");
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        String expectedToken = "mocked-jwt-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(request.username());
        when(jwtUtil.generateToken(request.username())).thenReturn(expectedToken);

        // Act
        String token = authService.authenticateUser(request);

        // Assert
        assertNotNull(token);
        assertEquals(expectedToken, token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken(request.username());
    }

    @Test
    public void shouldReturnUserInfoWhenTokenIsValid() {
        var token = "token test";
        var now = new Date();
        UserInfoRequest request = new UserInfoRequest(token);

        when(jwtUtil.getUsernameFromToken(token)).thenReturn("user@gmail.com");
        when(jwtUtil.getExpirationDateFromToken(token)).thenReturn(now);
        when(jwtUtil.getIssueDateFromToken(token)).thenReturn(now);

        var response = authService.me(request);

        assertNotNull(response);
        assertEquals("user@gmail.com", response.username());
        assertEquals(now, response.expirationDate());
        assertEquals(now, response.issueDate());

        verify(jwtUtil).getUsernameFromToken(token);
    }

    @Test
    public void shouldThrowExceptionWhenCredentialsAreInvalid() {
        SignInRequest request = new SignInRequest("user@email.com", "password123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("Failed"));

        assertThrows(BadCredentialsException.class, () -> authService.authenticateUser(request));
    }

    @Test
    public void shouldThrowExceptionWhenDuplicatedUser() {
        SignUpRequest request = new SignUpRequest("name", "123", "email@gmail.com", "password123");

        when(usersRepository.findByEmail(request.email())).thenReturn(Optional.of(new Users()));

        assertThrows(DuplicateUserException.class, () -> authService.registerUser(request));

        verify(usersRepository, never()).save(any());
        verifyNoInteractions(accountClient);
    }

    @Test
    public void shouldRegisterUserSuccessfully() {
        SignUpRequest request = new SignUpRequest("John Doe", "12345678901", "john@email.com", "password");
        Users user = new Users();
        user.setCpf(request.cpf());

        when(usersRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(encoder.encode(anyString())).thenReturn("Hashed password");
        when(usersRepository.save(any(Users.class))).thenReturn(user);
        when(accountClient.createAccountOnSignUp(any())).thenReturn(ResponseEntity.ok().build());

        var response = authService.registerUser(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        verify(usersRepository).save(any(Users.class));
        verify(accountClient).createAccountOnSignUp(any());
    }
}
