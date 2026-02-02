package com.smartgym.smartgym.dto.response;

import java.time.LocalDateTime;

public record WorkoutClassResponse(
        Long id,
        String title,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int capacity,
        Long trainerId,
        String trainerName
) {}
