package com.smartgym.smartgym.dto.response;

import java.time.LocalDateTime;

public record MemberResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDateTime createdAt
) {}
