package com.finconnect.auth_service.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.finconnect.auth_service.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String>{
    
    Optional<Users> findByEmail(String email);

    Optional<Users> findByCpf(String cpf);

    @Query("SELECT u.email FROM users u WHERE u.cpf = :cpf")
    Optional<String> findEmailByCpf(String cpf);
}
