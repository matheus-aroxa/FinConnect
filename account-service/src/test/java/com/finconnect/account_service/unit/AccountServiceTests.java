package com.finconnect.account_service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.finconnect.account_service.dto.AccountInfoResponse;
import com.finconnect.account_service.dto.AccountResponse;
import com.finconnect.account_service.dto.CreateAccount;
import com.finconnect.account_service.dto.CreditAccountRequest;
import com.finconnect.account_service.dto.DebtFromAccountRequest;
import com.finconnect.account_service.entity.Account;
import com.finconnect.account_service.entity.Status;
import com.finconnect.account_service.exception.exceptions.AccountNotFoundException;
import com.finconnect.account_service.exception.exceptions.InsufficientBalanceException;
import com.finconnect.account_service.repository.AccountRepository;
import com.finconnect.account_service.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {
    
    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void debitAmountFromAccountSuccessfully() {
        String cpf = "10231791054";
        BigDecimal balance = new BigDecimal("100.00");
        BigDecimal debAmount = new BigDecimal("30.00");

        Account account = new Account();
        account.setCpf(cpf);
        account.setBalance(balance);

        DebtFromAccountRequest request = new DebtFromAccountRequest(cpf, debAmount);

        when(repository.findAccountByCpf(cpf)).thenReturn(Optional.of(account));
        when(repository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        AccountResponse response = accountService.debitAmountFromAccount(request);

        assertEquals(new BigDecimal("70.00"), response.balance());
        verify(repository, times(1)).save(any(Account.class));
    }

    @Test
    void shoulThrowExceptionWhenBalanceIsInsufficient() {
        String cpf = "12345678900";
        Account account = new Account();
        account.setBalance(new BigDecimal("10.00"));

        DebtFromAccountRequest request = new DebtFromAccountRequest(cpf, new BigDecimal("50.00"));

        when(repository.findAccountByCpf(cpf)).thenReturn(Optional.of(account));

        assertThrows(InsufficientBalanceException.class, () -> accountService.debitAmountFromAccount(request));
        verify(repository, never()).save(any(Account.class));
    }

    @Test
    void shoulThrowExceptionWhenAccountByCpfNotFound() {
        String cpf = "12345678900";
        DebtFromAccountRequest request = new DebtFromAccountRequest(cpf, null);

        when(repository.findAccountByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.debitAmountFromAccount(request));
    }

    @Test
    void shoulThrowExceptionWhenAccountNotFound() {
        String cpf = "12345678900";

        when(repository.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountInfo(cpf));
    }

    @Test
    void shouldGetAccountInfoSuccessfully() {
        String cpf = "12345678900";
        Account account = new Account();
        account.setAccountNumber("54321");
        account.setAgency("0001");
        account.setBalance(new BigDecimal("500.00"));

        when(repository.findByCpf(cpf)).thenReturn(Optional.of(account));

        AccountInfoResponse response = accountService.getAccountInfo(cpf);

        assertEquals("54321", response.accountNumber());
        assertEquals("0001", response.agency());
        assertEquals(new BigDecimal("500.00"), response.balance());
    }

    @Test
    void creditAmountFromAccountSuccessfully() {
        String cpf = "10231791054";
        BigDecimal balance = new BigDecimal("100.00");
        BigDecimal creditAmount = new BigDecimal("30.00");

        Account account = new Account();
        account.setCpf(cpf);
        account.setBalance(balance);

        CreditAccountRequest request = new CreditAccountRequest(cpf, creditAmount);

        when(repository.findAccountByCpf(cpf)).thenReturn(Optional.of(account));
        when(repository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        AccountResponse response = accountService.creditAccount(request);

        assertEquals(new BigDecimal("130.00"), response.balance());
        verify(repository, times(1)).save(any(Account.class));
    }

    @Test
    void createAccountOnSignUpSuccessfully() {
        CreateAccount request = new CreateAccount("10231791054");
        when(repository.save(any(Account.class))).thenAnswer(i -> {
            Account acc = (Account) i.getArguments()[0];
            acc.setAccountNumber("54321");
            return acc;
        });

        AccountResponse response = accountService.createAccountOnSignUp(request);

        assertEquals("10231791054", response.cpf());
        assertNotNull(response.accountNumber());
        assertNotNull(response.agency());
        assertEquals(new BigDecimal("0.0"), response.balance());
        assertEquals(Status.ACTIVE, response.status());
    }
}
