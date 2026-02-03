package com.smartgym.smartgym.controller;

import com.smartgym.smartgym.dto.response.RevenueMonthResponse;
import com.smartgym.smartgym.dto.response.RevenueYearResponse;
import com.smartgym.smartgym.service.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // /api/reports/revenue?year=2026&month=2  -> single month
    // /api/reports/revenue?year=2026         -> full year breakdown
    @GetMapping("/revenue")
    public Object revenue(@RequestParam int year,
                          @RequestParam(required = false) Integer month) {

        if (month != null) {
            RevenueMonthResponse r = reportService.revenueForMonth(year, month);
            return r;
        }

        RevenueYearResponse y = reportService.revenueForYear(year);
        return y;
    }
}
