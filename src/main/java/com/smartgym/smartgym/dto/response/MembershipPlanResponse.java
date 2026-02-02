package com.smartgym.smartgym.dto.response;

import java.math.BigDecimal;

public record MembershipPlanResponse(
        Long id,
        String name,
        int durationDays,
        BigDecimal price
) {}
