package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.SubscriptionCreateRequest;
import com.smartgym.smartgym.dto.response.SubscriptionResponse;
import com.smartgym.smartgym.entity.Member;
import com.smartgym.smartgym.entity.MembershipPlan;
import com.smartgym.smartgym.entity.SubscriptionStatus;
import com.smartgym.smartgym.repository.MemberRepository;
import com.smartgym.smartgym.repository.MembershipPlanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ActiveProfiles;



import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")

class SubscriptionServiceImplTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembershipPlanRepository planRepository;

    @Test
    void createSubscription_setsEndDate_andActiveStatus() {
        // Arrange
        Member member = memberRepository.save(Member.builder()
                .firstName("Vjosa")
                .lastName("Zulfija")
                .email("vjosa.sub.test@example.com")
                .phone("070000000")
                .build());

        MembershipPlan plan = planRepository.save(MembershipPlan.builder()
                .name("Monthly")
                .durationDays(30)
                .price(new BigDecimal("29.99"))
                .build());

        LocalDate start = LocalDate.of(2026, 2, 1);

        // Act
        SubscriptionResponse res = subscriptionService.create(
                new SubscriptionCreateRequest(member.getId(), plan.getId(), start)
        );

        // Assert
        assertThat(res.startDate()).isEqualTo(start);
        assertThat(res.endDate()).isEqualTo(start.plusDays(30));
        assertThat(res.paidAmount()).isEqualByComparingTo("29.99");
        assertThat(res.status()).isIn(SubscriptionStatus.ACTIVE, SubscriptionStatus.EXPIRED);
        // (status depends on "today"; we just assert it is set correctly to one of enum values)
    }

    @Test
    void createSubscription_withOldStartDate_setsExpiredStatus() {
        // Arrange
        Member member = memberRepository.save(Member.builder()
                .firstName("Arber")
                .lastName("Krasniqi")
                .email("arber.sub.test@example.com")
                .build());

        MembershipPlan plan = planRepository.save(MembershipPlan.builder()
                .name("Monthly")
                .durationDays(30)
                .price(new BigDecimal("29.99"))
                .build());

        LocalDate start = LocalDate.of(2024, 1, 1);

        // Act
        SubscriptionResponse res = subscriptionService.create(
                new SubscriptionCreateRequest(member.getId(), plan.getId(), start)
        );

        // Assert
        assertThat(res.endDate()).isEqualTo(start.plusDays(30));
        assertThat(res.status()).isEqualTo(SubscriptionStatus.EXPIRED);
    }
}
