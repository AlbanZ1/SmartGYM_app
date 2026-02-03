package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.response.RevenueMonthResponse;
import com.smartgym.smartgym.dto.response.RevenueYearResponse;

public interface ReportService {
    RevenueMonthResponse revenueForMonth(int year, int month);
    RevenueYearResponse revenueForYear(int year);
}
