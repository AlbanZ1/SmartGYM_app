package com.smartgym.smartgym.controller;

import com.smartgym.smartgym.dto.request.MembershipPlanCreateRequest;
import com.smartgym.smartgym.dto.response.MembershipPlanResponse;
import com.smartgym.smartgym.service.MembershipPlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class MembershipPlanController {

    private final MembershipPlanService planService;

    public MembershipPlanController(MembershipPlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MembershipPlanResponse create(@Valid @RequestBody MembershipPlanCreateRequest request) {
        return planService.create(request);
    }

    @GetMapping("/{id}")
    public MembershipPlanResponse getById(@PathVariable Long id) {
        return planService.getById(id);
    }

    @GetMapping
    public List<MembershipPlanResponse> getAll() {
        return planService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        planService.delete(id);
    }
}
