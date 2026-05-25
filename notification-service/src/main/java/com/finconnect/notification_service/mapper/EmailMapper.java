package com.finconnect.notification_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.finconnect.notification_service.dto.SendEmailRequest;
import com.finconnect.notification_service.entity.Email;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "origin", target = "origin")
    @Mapping(source = "request.message", target = "content")
    Email toEntity(SendEmailRequest request, String origin);
}
