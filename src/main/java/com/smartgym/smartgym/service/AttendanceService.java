package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.AttendanceCreateRequest;
import com.smartgym.smartgym.dto.response.AttendanceResponse;

public interface AttendanceService {
    AttendanceResponse register(AttendanceCreateRequest request);
}
