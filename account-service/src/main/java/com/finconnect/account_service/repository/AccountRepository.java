package com.finconnect.account_service.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.finconnect.account_service.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
    
    Optional<Account> findAccountByCpf(String cpf);
}
