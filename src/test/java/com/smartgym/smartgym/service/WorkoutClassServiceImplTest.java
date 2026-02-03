package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.WorkoutClassCreateRequest;
import com.smartgym.smartgym.entity.Trainer;
import com.smartgym.smartgym.exception.TrainerScheduleConflictException;
import com.smartgym.smartgym.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ActiveProfiles;



import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@ActiveProfiles("test")

class WorkoutClassServiceImplTest {

    @Autowired
    private WorkoutClassService workoutClassService;

    @Autowired
    private TrainerRepository trainerRepository;

    @Test
    void createClass_whenOverlapsForSameTrainer_throwsConflict() {
        // Arrange
        Trainer trainer = trainerRepository.save(Trainer.builder()
                .fullName("Arben Trainer")
                .specialty("Strength")
                .email("trainer.overlap.test@example.com")
                .build());

        // Create initial class 10:00 - 11:00
        workoutClassService.create(new WorkoutClassCreateRequest(
                "Leg Day",
                LocalDateTime.of(2026, 2, 3, 10, 0),
                LocalDateTime.of(2026, 2, 3, 11, 0),
                10,
                trainer.getId()
        ));

        // Act + Assert: overlapping 10:30 - 11:30 should throw
        assertThatThrownBy(() -> workoutClassService.create(new WorkoutClassCreateRequest(
                "Overlap Class",
                LocalDateTime.of(2026, 2, 3, 10, 30),
                LocalDateTime.of(2026, 2, 3, 11, 30),
                10,
                trainer.getId()
        ))).isInstanceOf(TrainerScheduleConflictException.class);
    }
}
