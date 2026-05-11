package com.finconnect.account_service.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import com.finconnect.account_service.entity.Account;
import jakarta.persistence.LockModeType;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findAccountByCpf(String cpf);

    // Use this for simple reads (getAccountInfo)
    Optional<Account> findByCpf(String cpf); 
}
