package com.finconnect.auth_service.dto;

import java.util.Date;

public record UserInfoFromJwtResponse(
    String username,
    Date expirationDate,
    Date issueDate
) {}
