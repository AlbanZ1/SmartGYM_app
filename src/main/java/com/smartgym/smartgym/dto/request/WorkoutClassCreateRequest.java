package com.smartgym.smartgym.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record WorkoutClassCreateRequest(
        @NotBlank String title,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime,
        @Min(1) int capacity,
        @NotNull Long trainerId
) {}
