package com.smartgym.smartgym.dto.request;

import jakarta.validation.constraints.NotNull;

public record AttendanceCreateRequest(
        @NotNull Long memberId,
        @NotNull Long classId
) {}
