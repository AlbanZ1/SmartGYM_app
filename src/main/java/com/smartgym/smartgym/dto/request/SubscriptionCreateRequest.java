package com.smartgym.smartgym.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SubscriptionCreateRequest(
        @NotNull Long memberId,
        @NotNull Long planId,
        // if null, we will default to today
        LocalDate startDate
) {}
