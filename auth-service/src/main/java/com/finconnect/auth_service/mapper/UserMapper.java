package com.finconnect.auth_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.finconnect.auth_service.dto.CreateAccount;
import com.finconnect.auth_service.dto.SignUpRequest;
import com.finconnect.auth_service.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Users toEntity(SignUpRequest request);

    CreateAccount toCreateAccount(Users user);
}
