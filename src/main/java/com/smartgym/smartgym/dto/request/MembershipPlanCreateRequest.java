package com.smartgym.smartgym.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MembershipPlanCreateRequest(
        @NotBlank String name,
        @Min(1) int durationDays,
        @NotNull @Min(0) BigDecimal price
) {}
