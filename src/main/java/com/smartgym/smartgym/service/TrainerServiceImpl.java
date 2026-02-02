package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.TrainerCreateRequest;
import com.smartgym.smartgym.dto.response.TrainerResponse;
import com.smartgym.smartgym.entity.Trainer;
import com.smartgym.smartgym.exception.BadRequestException;
import com.smartgym.smartgym.exception.NotFoundException;
import com.smartgym.smartgym.repository.TrainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public TrainerResponse create(TrainerCreateRequest request) {
        if (trainerRepository.existsByEmailIgnoreCase(request.email())) {
            throw new BadRequestException("Trainer email already exists: " + request.email());
        }

        Trainer t = Trainer.builder()
                .fullName(request.fullName())
                .specialty(request.specialty())
                .email(request.email())
                .build();

        return toResponse(trainerRepository.save(t));
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerResponse getById(Long id) {
        Trainer t = trainerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainer not found: " + id));
        return toResponse(t);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainerResponse> getAll(String q) {
        List<Trainer> list = trainerRepository.findAll();
        if (q == null || q.isBlank()) return list.stream().map(this::toResponse).toList();

        String query = q.toLowerCase();
        return list.stream()
                .filter(t ->
                        (t.getFullName() != null && t.getFullName().toLowerCase().contains(query)) ||
                                (t.getSpecialty() != null && t.getSpecialty().toLowerCase().contains(query)) ||
                                (t.getEmail() != null && t.getEmail().toLowerCase().contains(query))
                )
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!trainerRepository.existsById(id)) {
            throw new NotFoundException("Trainer not found: " + id);
        }
        trainerRepository.deleteById(id);
    }

    private TrainerResponse toResponse(Trainer t) {
        return new TrainerResponse(t.getId(), t.getFullName(), t.getSpecialty(), t.getEmail());
    }
}
