package com.smartgym.smartgym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgym.smartgym.dto.request.WorkoutClassCreateRequest;
import com.smartgym.smartgym.exception.TrainerScheduleConflictException;
import com.smartgym.smartgym.exception.handler.GlobalExceptionHandler;
import com.smartgym.smartgym.service.WorkoutClassService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkoutClassController.class)
@Import(GlobalExceptionHandler.class)
class WorkoutClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();


    @MockitoBean
    private WorkoutClassService workoutClassService;

    @Test
    void createWorkoutClass_whenScheduleConflict_returns409() throws Exception {

        // Arrange
        WorkoutClassCreateRequest request = new WorkoutClassCreateRequest(
                "Overlapping Class",
                LocalDateTime.of(2026, 2, 3, 10, 30),
                LocalDateTime.of(2026, 2, 3, 11, 30),
                10,
                1L
        );

        when(workoutClassService.create(request))
                .thenThrow(new TrainerScheduleConflictException("Trainer is already booked"));

        // Act + Assert
        mockMvc.perform(post("/api/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Trainer is already booked"));
    }
}
