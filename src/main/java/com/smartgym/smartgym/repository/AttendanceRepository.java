package com.smartgym.smartgym.repository;

import com.smartgym.smartgym.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    long countByWorkoutClassId(Long classId);

    boolean existsByMemberIdAndWorkoutClassId(Long memberId, Long classId);
}
