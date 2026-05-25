package com.finconnect.transaction_service.service;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.finconnect.transaction_service.dto.CreditAccountRequest;
import com.finconnect.transaction_service.dto.DebtFromAccountRequest;
import com.finconnect.transaction_service.dto.EmailFromCpfRequest;
import com.finconnect.transaction_service.dto.SendEmailResquest;
import com.finconnect.transaction_service.dto.StatementResponse;
import com.finconnect.transaction_service.dto.TransferRequest;
import com.finconnect.transaction_service.entity.Status;
import com.finconnect.transaction_service.entity.Transaction;
import com.finconnect.transaction_service.entity.Type;
import com.finconnect.transaction_service.feign.AccountClient;
import com.finconnect.transaction_service.feign.AuthClient;
import com.finconnect.transaction_service.mapper.TransactionMapper;
import com.finconnect.transaction_service.repository.TransactionRepository;
import org.springframework.data.domain.Pageable;

@Service
public class TransactionService {
    
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private ReceiptProducerService receiptProducerService;

    @Autowired
    private TransactionMapper transactionMapper;

    //---------------------------//-------------------------//--------------------------------//------------------
    @Transactional
    public String transfer(TransferRequest request) {
        logger.info("Trying to transfer between accounts");
        
        Transaction transaction = new Transaction();
        transaction.setOriginCpf(request.origin());
        transaction.setDestinationCpf(request.destination());
        transaction.setAmount(request.amount());
        transaction.setCreatedAt(new Date());
        transaction.setTransactionType(Type.TRANSFER);

        try {
            accountClient.debitAmountFromAccount(new DebtFromAccountRequest(request.origin(), request.amount()));

            accountClient.creditAccount(new CreditAccountRequest(request.destination(), request.amount()));
            transaction.setTransactionStatus(Status.COMPLETED);

            this.transactionRepository.save(transaction);

            this.receiptProducerService.sendMessage(
                new SendEmailResquest(authClient.findEmailFromCpf(new EmailFromCpfRequest(request.origin())).getBody().email(), "Transaction completed", "The transaction completed successfully."));

            this.receiptProducerService.sendMessage(
                new SendEmailResquest(authClient.findEmailFromCpf(new EmailFromCpfRequest(request.destination())).getBody().email(), "Transaction completed", "The transaction completed successfully."));
            
            return "Transaction completed";
        } catch (Exception e) {
            logger.error("Transaction failed");
            logger.error("Error: " + e.getMessage());
            logger.error("StackTrace: " + e.getStackTrace());
            transaction.setTransactionStatus(Status.FAILED);
            this.transactionRepository.save(transaction);
            return "Transaction failed";
        }
    }
    //---------------------------//-------------------------//--------------------------------//------------------
    public StatementResponse getStatement(String cpf) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
        
        var transactions = this.transactionRepository.findByOriginCpf(cpf, pageable);
        return new StatementResponse(transactionMapper.toResponseList(transactions));
    }
}
