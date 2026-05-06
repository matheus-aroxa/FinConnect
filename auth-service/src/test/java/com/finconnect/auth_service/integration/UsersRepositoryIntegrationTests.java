package com.finconnect.auth_service.integration;

import static org.junit.Assert.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import com.finconnect.auth_service.container.PostgresTestContainer;
import com.finconnect.auth_service.entity.Users;
import com.finconnect.auth_service.repository.UsersRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersRepositoryIntegrationTests extends PostgresTestContainer {
    
    @Autowired
    private UsersRepository repository;

    @Test
    void shouldPersistAndRetrieveUser() {
        Users user = new Users();
        user.setFullName("Integration user");
        user.setEmail("test@gmail.com");
        user.setCpf("53534988019");
        user.setPassword("secret");

        repository.save(user);

        Optional<Users> found = repository.findByEmail("test@gmail.com");

        assertTrue(found.isPresent());
        assertEquals("Integration user", user.getFullName());
    }
}
