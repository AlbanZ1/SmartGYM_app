package com.smartgym.smartgym.controller;

import com.smartgym.smartgym.dto.request.WorkoutClassCreateRequest;
import com.smartgym.smartgym.dto.response.WorkoutClassResponse;
import com.smartgym.smartgym.service.WorkoutClassService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class WorkoutClassController {

    private final WorkoutClassService workoutClassService;

    public WorkoutClassController(WorkoutClassService workoutClassService) {
        this.workoutClassService = workoutClassService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutClassResponse create(@Valid @RequestBody WorkoutClassCreateRequest request) {
        return workoutClassService.create(request);
    }

    @GetMapping("/{id}")
    public WorkoutClassResponse getById(@PathVariable Long id) {
        return workoutClassService.getById(id);
    }

    // Optional filters: trainerId and date (YYYY-MM-DD)
    @GetMapping
    public List<WorkoutClassResponse> getAll(
            @RequestParam(required = false) Long trainerId,
            @RequestParam(required = false) LocalDate date
    ) {
        return workoutClassService.getAll(trainerId, date);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        workoutClassService.delete(id);
    }
}
