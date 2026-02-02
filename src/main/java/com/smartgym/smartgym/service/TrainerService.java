package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.TrainerCreateRequest;
import com.smartgym.smartgym.dto.response.TrainerResponse;

import java.util.List;

public interface TrainerService {
    TrainerResponse create(TrainerCreateRequest request);
    TrainerResponse getById(Long id);
    List<TrainerResponse> getAll(String q);
    void delete(Long id);
}
