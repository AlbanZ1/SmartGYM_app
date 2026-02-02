package com.smartgym.smartgym.dto.response;

import com.smartgym.smartgym.entity.SubscriptionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SubscriptionResponse(
        Long id,
        Long memberId,
        Long planId,
        LocalDate startDate,
        LocalDate endDate,
        SubscriptionStatus status,
        BigDecimal paidAmount
) {}
