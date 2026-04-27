package com.finconnect.account_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finconnect.account_service.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>{
    
}
