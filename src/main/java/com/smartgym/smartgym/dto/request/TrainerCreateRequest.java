package com.smartgym.smartgym.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TrainerCreateRequest(
        @NotBlank String fullName,
        String specialty,
        @Email @NotBlank String email
) {}
