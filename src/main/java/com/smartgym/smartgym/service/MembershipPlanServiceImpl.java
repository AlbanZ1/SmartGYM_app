package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.MembershipPlanCreateRequest;
import com.smartgym.smartgym.dto.response.MembershipPlanResponse;
import com.smartgym.smartgym.entity.MembershipPlan;
import com.smartgym.smartgym.exception.NotFoundException;
import com.smartgym.smartgym.repository.MembershipPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MembershipPlanServiceImpl implements MembershipPlanService {

    private final MembershipPlanRepository planRepository;

    public MembershipPlanServiceImpl(MembershipPlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public MembershipPlanResponse create(MembershipPlanCreateRequest request) {
        MembershipPlan plan = MembershipPlan.builder()
                .name(request.name())
                .durationDays(request.durationDays())
                .price(request.price())
                .build();

        MembershipPlan saved = planRepository.save(plan);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MembershipPlanResponse getById(Long id) {
        MembershipPlan p = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan not found: " + id));
        return toResponse(p);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembershipPlanResponse> getAll() {
        return planRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        if (!planRepository.existsById(id)) {
            throw new NotFoundException("Plan not found: " + id);
        }
        planRepository.deleteById(id);
    }

    private MembershipPlanResponse toResponse(MembershipPlan p) {
        return new MembershipPlanResponse(
                p.getId(),
                p.getName(),
                p.getDurationDays(),
                p.getPrice()
        );
    }
}
