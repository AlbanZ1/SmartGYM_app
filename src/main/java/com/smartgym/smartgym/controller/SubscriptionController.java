package com.smartgym.smartgym.controller;

import com.smartgym.smartgym.dto.request.SubscriptionCreateRequest;
import com.smartgym.smartgym.dto.response.SubscriptionResponse;
import com.smartgym.smartgym.entity.SubscriptionStatus;
import com.smartgym.smartgym.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public SubscriptionResponse create(@Valid @RequestBody SubscriptionCreateRequest request) {
        return subscriptionService.create(request);
    }

    @GetMapping("/latest/{memberId}")
    public SubscriptionResponse latest(@PathVariable Long memberId) {
        return subscriptionService.getLatestForMember(memberId);
    }

    @GetMapping
    public List<SubscriptionResponse> byStatus(@RequestParam SubscriptionStatus status) {
        return subscriptionService.getByStatus(status);
    }
}
