package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.SubscriptionCreateRequest;
import com.smartgym.smartgym.dto.response.SubscriptionResponse;
import com.smartgym.smartgym.entity.SubscriptionStatus;

import java.util.List;

public interface SubscriptionService {

    SubscriptionResponse create(SubscriptionCreateRequest request);

    SubscriptionResponse getLatestForMember(Long memberId);

    List<SubscriptionResponse> getByStatus(SubscriptionStatus status);
}
