package com.smartgym.smartgym.repository;

import com.smartgym.smartgym.entity.WorkoutClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutClassRepository extends JpaRepository<WorkoutClass, Long> {

    // Overlap rule:
    // existing.start < newEnd AND existing.end > newStart
    @Query("""
        select wc from WorkoutClass wc
        where wc.trainer.id = :trainerId
          and wc.startTime < :newEnd
          and wc.endTime > :newStart
    """)
    List<WorkoutClass> findOverlappingForTrainer(
            @Param("trainerId") Long trainerId,
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd
    );
}
