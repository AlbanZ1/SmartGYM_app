package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.MembershipPlanCreateRequest;
import com.smartgym.smartgym.dto.response.MembershipPlanResponse;

import java.util.List;

public interface MembershipPlanService {
    MembershipPlanResponse create(MembershipPlanCreateRequest request);
    MembershipPlanResponse getById(Long id);
    List<MembershipPlanResponse> getAll();
    void delete(Long id);
}
