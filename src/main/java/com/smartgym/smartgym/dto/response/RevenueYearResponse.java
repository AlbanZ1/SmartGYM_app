package com.smartgym.smartgym.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record RevenueYearResponse(
        int year,
        BigDecimal totalRevenue,
        List<RevenueMonthResponse> months
) {}
