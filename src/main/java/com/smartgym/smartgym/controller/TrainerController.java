package com.smartgym.smartgym.controller;

import com.smartgym.smartgym.dto.request.TrainerCreateRequest;
import com.smartgym.smartgym.dto.response.TrainerResponse;
import com.smartgym.smartgym.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainerResponse create(@Valid @RequestBody TrainerCreateRequest request) {
        return trainerService.create(request);
    }

    @GetMapping("/{id}")
    public TrainerResponse getById(@PathVariable Long id) {
        return trainerService.getById(id);
    }

    @GetMapping
    public List<TrainerResponse> getAll(@RequestParam(required = false) String q) {
        return trainerService.getAll(q);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        trainerService.delete(id);
    }
}
