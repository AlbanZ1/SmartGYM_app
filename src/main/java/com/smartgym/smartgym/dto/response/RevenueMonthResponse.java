package com.smartgym.smartgym.dto.response;

import java.math.BigDecimal;

public record RevenueMonthResponse(
        int year,
        int month,
        BigDecimal revenue
) {}
