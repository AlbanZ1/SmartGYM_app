package com.smartgym.smartgym.controller;

import com.smartgym.smartgym.dto.request.AttendanceCreateRequest;
import com.smartgym.smartgym.dto.response.AttendanceResponse;
import com.smartgym.smartgym.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceResponse register(@Valid @RequestBody AttendanceCreateRequest request) {
        return attendanceService.register(request);
    }
}
