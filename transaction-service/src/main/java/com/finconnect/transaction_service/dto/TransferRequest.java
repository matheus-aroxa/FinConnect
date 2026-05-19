package com.finconnect.transaction_service.dto;

import java.math.BigDecimal;
import org.hibernate.validator.constraints.br.CPF;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to transfer amount")
public record TransferRequest(

    @Schema(
        description = "The cpf from the owner of the origin account",
        example = "XXXXXXXXXXX",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @CPF
    String origin, //cpf

    @Schema(
        description = "The cpf from the owner of the destination account",
        example = "XXXXXXXXXXX",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @CPF
    String destination, //cpf

    @Schema(
        description = "Amount to be transfered",
        example = "0.0",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    BigDecimal amount
) {}
