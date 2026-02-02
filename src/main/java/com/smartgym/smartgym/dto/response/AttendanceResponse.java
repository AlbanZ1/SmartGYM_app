package com.smartgym.smartgym.dto.response;

import java.time.LocalDateTime;

public record AttendanceResponse(
        Long id,
        Long memberId,
        Long classId,
        LocalDateTime attendedAt
) {}
