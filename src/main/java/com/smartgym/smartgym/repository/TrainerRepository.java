package com.smartgym.smartgym.repository;

import com.smartgym.smartgym.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    boolean existsByEmailIgnoreCase(String email);
}
