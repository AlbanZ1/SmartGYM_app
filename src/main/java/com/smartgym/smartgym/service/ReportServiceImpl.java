package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.response.RevenueMonthResponse;
import com.smartgym.smartgym.dto.response.RevenueYearResponse;
import com.smartgym.smartgym.entity.Subscription;
import com.smartgym.smartgym.exception.BadRequestException;
import com.smartgym.smartgym.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final SubscriptionRepository subscriptionRepository;

    public ReportServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public RevenueMonthResponse revenueForMonth(int year, int month) {
        if (month < 1 || month > 12) {
            throw new BadRequestException("month must be between 1 and 12");
        }

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate endExclusive = start.plusMonths(1);

        List<Subscription> subs = subscriptionRepository.findByPaymentDateBetween(start, endExclusive.minusDays(1));

        BigDecimal revenue = subs.stream()
                .map(s -> s.getPaidAmount() == null ? BigDecimal.ZERO : s.getPaidAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RevenueMonthResponse(year, month, revenue);
    }

    @Override
    public RevenueYearResponse revenueForYear(int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);

        List<Subscription> subs = subscriptionRepository.findByPaymentDateBetween(start, end);

        // revenue per month 1..12
        List<BigDecimal> perMonth = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) perMonth.add(BigDecimal.ZERO);

        for (Subscription s : subs) {
            if (s.getPaymentDate() == null) continue;
            int m = s.getPaymentDate().getMonthValue(); // 1..12
            BigDecimal amt = (s.getPaidAmount() == null) ? BigDecimal.ZERO : s.getPaidAmount();
            perMonth.set(m - 1, perMonth.get(m - 1).add(amt));
        }

        List<RevenueMonthResponse> months = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (int m = 1; m <= 12; m++) {
            BigDecimal rev = perMonth.get(m - 1);
            total = total.add(rev);
            months.add(new RevenueMonthResponse(year, m, rev));
        }

        return new RevenueYearResponse(year, total, months);
    }
}
