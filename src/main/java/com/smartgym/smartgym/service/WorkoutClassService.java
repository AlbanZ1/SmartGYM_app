package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.WorkoutClassCreateRequest;
import com.smartgym.smartgym.dto.response.WorkoutClassResponse;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutClassService {

    WorkoutClassResponse create(WorkoutClassCreateRequest request);

    WorkoutClassResponse getById(Long id);

    List<WorkoutClassResponse> getAll(Long trainerId, LocalDate date);

    void delete(Long id);
}
