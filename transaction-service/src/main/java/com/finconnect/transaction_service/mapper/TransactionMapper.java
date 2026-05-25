package com.finconnect.transaction_service.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.finconnect.transaction_service.dto.TransactionResponse;
import com.finconnect.transaction_service.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionResponse toResponse(Transaction transaction);

    List<TransactionResponse> toResponseList(List<Transaction> transactions);
}
