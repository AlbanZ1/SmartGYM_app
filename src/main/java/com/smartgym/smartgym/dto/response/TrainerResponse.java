package com.smartgym.smartgym.dto.response;

public record TrainerResponse(
        Long id,
        String fullName,
        String specialty,
        String email
) {}
