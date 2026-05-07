package com.finconnect.auth_service.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.finconnect.auth_service.entity.Users;
import com.finconnect.auth_service.repository.UsersRepository;
import com.finconnect.auth_service.service.CustomUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTests {
    
    @Mock
    private UsersRepository repository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
        String email = "test@gmail.com";
        Users user = new Users();
        user.setEmail(email);
        user.setPassword("hashed_password");

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        
        UserDetails result = userDetailsService.loadUserByUsername(email);

        assertNotNull(result);
        assertEquals(email, result.getUsername());
        assertEquals("hashed_password", result.getPassword());
    }

    @Test
    void loadUserByUsernameShouldThrowExceptionWhenUserDoesNotExists() {
        String email = "notfound@gmail.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());
        
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(email);
        });
    }
}
