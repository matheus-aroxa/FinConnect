package com.finconnect.account_service.mapper;

import org.mapstruct.Mapper;
import com.finconnect.account_service.dto.AccountInfoResponse;
import com.finconnect.account_service.dto.AccountResponse;
import com.finconnect.account_service.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toResponse(Account account);

    AccountInfoResponse toInfoResponse(Account account);
}
