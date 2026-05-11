package com.finconnect.transaction_service.dto;

import java.util.List;
import com.finconnect.transaction_service.entity.Transaction;

public record StatementResponse(
    List<Transaction> transactions
) {}