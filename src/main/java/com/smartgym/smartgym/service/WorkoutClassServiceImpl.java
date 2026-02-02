package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.WorkoutClassCreateRequest;
import com.smartgym.smartgym.dto.response.WorkoutClassResponse;
import com.smartgym.smartgym.entity.Trainer;
import com.smartgym.smartgym.entity.WorkoutClass;
import com.smartgym.smartgym.exception.BadRequestException;
import com.smartgym.smartgym.exception.NotFoundException;
import com.smartgym.smartgym.exception.TrainerScheduleConflictException;
import com.smartgym.smartgym.repository.TrainerRepository;
import com.smartgym.smartgym.repository.WorkoutClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class WorkoutClassServiceImpl implements WorkoutClassService {

    private final WorkoutClassRepository workoutClassRepository;
    private final TrainerRepository trainerRepository;

    public WorkoutClassServiceImpl(
            WorkoutClassRepository workoutClassRepository,
            TrainerRepository trainerRepository
    ) {
        this.workoutClassRepository = workoutClassRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public WorkoutClassResponse create(WorkoutClassCreateRequest request) {
        Trainer trainer = trainerRepository.findById(request.trainerId())
                .orElseThrow(() -> new NotFoundException("Trainer not found: " + request.trainerId()));

        LocalDateTime start = request.startTime();
        LocalDateTime end = request.endTime();

        if (!end.isAfter(start)) {
            throw new BadRequestException("endTime must be after startTime");
        }

        // Schedule conflict check (no overlap for same trainer)
        List<WorkoutClass> overlaps = workoutClassRepository.findOverlappingForTrainer(
                trainer.getId(),
                start,
                end
        );

        if (!overlaps.isEmpty()) {
            throw new TrainerScheduleConflictException(
                    "Trainer is already booked between " + start + " and " + end
            );
        }

        WorkoutClass wc = WorkoutClass.builder()
                .title(request.title())
                .startTime(start)
                .endTime(end)
                .capacity(request.capacity())
                .trainer(trainer)
                .build();

        return toResponse(workoutClassRepository.save(wc));
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutClassResponse getById(Long id) {
        WorkoutClass wc = workoutClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("WorkoutClass not found: " + id));
        return toResponse(wc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutClassResponse> getAll(Long trainerId, LocalDate date) {

        // Keep it simple for now: fetch all, then filter in memory.
        // (Later we can add repository queries if needed.)
        List<WorkoutClass> list = workoutClassRepository.findAll();

        if (trainerId != null) {
            list = list.stream()
                    .filter(wc -> wc.getTrainer().getId().equals(trainerId))
                    .toList();
        }

        if (date != null) {
            LocalDateTime dayStart = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime dayEnd = LocalDateTime.of(date, LocalTime.MAX);

            list = list.stream()
                    .filter(wc -> !wc.getStartTime().isBefore(dayStart) && !wc.getStartTime().isAfter(dayEnd))
                    .toList();
        }

        return list.stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        if (!workoutClassRepository.existsById(id)) {
            throw new NotFoundException("WorkoutClass not found: " + id);
        }
        workoutClassRepository.deleteById(id);
    }

    private WorkoutClassResponse toResponse(WorkoutClass wc) {
        return new WorkoutClassResponse(
                wc.getId(),
                wc.getTitle(),
                wc.getStartTime(),
                wc.getEndTime(),
                wc.getCapacity(),
                wc.getTrainer().getId(),
                wc.getTrainer().getFullName()
        );
    }
}
