package com.finconnect.transaction_service.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The transactions history")
public record StatementResponse(

    @Schema(description = "List of transactions")
    List<TransactionResponse> transactions
) {}