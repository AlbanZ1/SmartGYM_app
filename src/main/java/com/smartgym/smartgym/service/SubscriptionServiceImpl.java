package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.SubscriptionCreateRequest;
import com.smartgym.smartgym.dto.response.SubscriptionResponse;
import com.smartgym.smartgym.entity.Member;
import com.smartgym.smartgym.entity.MembershipPlan;
import com.smartgym.smartgym.entity.Subscription;
import com.smartgym.smartgym.entity.SubscriptionStatus;
import com.smartgym.smartgym.exception.BadRequestException;
import com.smartgym.smartgym.exception.NotFoundException;
import com.smartgym.smartgym.repository.MemberRepository;
import com.smartgym.smartgym.repository.MembershipPlanRepository;
import com.smartgym.smartgym.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;
    private final MembershipPlanRepository planRepository;

    public SubscriptionServiceImpl(
            SubscriptionRepository subscriptionRepository,
            MemberRepository memberRepository,
            MembershipPlanRepository planRepository
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.memberRepository = memberRepository;
        this.planRepository = planRepository;
    }

    @Override
    public SubscriptionResponse create(SubscriptionCreateRequest request) {

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new NotFoundException("Member not found: " + request.memberId()));

        MembershipPlan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new NotFoundException("Plan not found: " + request.planId()));

        LocalDate start = (request.startDate() == null) ? LocalDate.now() : request.startDate();

        if (plan.getDurationDays() <= 0) {
            throw new BadRequestException("Plan durationDays must be > 0");
        }

        LocalDate end = start.plusDays(plan.getDurationDays());

        SubscriptionStatus status = LocalDate.now().isAfter(end) ? SubscriptionStatus.EXPIRED : SubscriptionStatus.ACTIVE;

        Subscription sub = Subscription.builder()
                .member(member)
                .plan(plan)
                .startDate(start)
                .endDate(end)
                .status(status)
                .paidAmount(plan.getPrice())
                .paymentDate(LocalDate.now())
                .build();

        Subscription saved = subscriptionRepository.save(sub);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SubscriptionResponse getLatestForMember(Long memberId) {
        Subscription sub = subscriptionRepository.findTopByMemberIdOrderByEndDateDesc(memberId)
                .orElseThrow(() -> new NotFoundException("No subscriptions found for member: " + memberId));
        return toResponse(sub);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getByStatus(SubscriptionStatus status) {
        return subscriptionRepository.findByStatus(status).stream()
                .map(this::toResponse)
                .toList();
    }

    private SubscriptionResponse toResponse(Subscription s) {
        return new SubscriptionResponse(
                s.getId(),
                s.getMember().getId(),
                s.getPlan().getId(),
                s.getStartDate(),
                s.getEndDate(),
                s.getStatus(),
                s.getPaidAmount()
        );
    }
}
