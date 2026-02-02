package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.AttendanceCreateRequest;
import com.smartgym.smartgym.dto.response.AttendanceResponse;
import com.smartgym.smartgym.entity.Attendance;
import com.smartgym.smartgym.entity.Member;
import com.smartgym.smartgym.entity.WorkoutClass;
import com.smartgym.smartgym.exception.AlreadyRegisteredException;
import com.smartgym.smartgym.exception.CapacityExceededException;
import com.smartgym.smartgym.exception.NotFoundException;
import com.smartgym.smartgym.repository.AttendanceRepository;
import com.smartgym.smartgym.repository.MemberRepository;
import com.smartgym.smartgym.repository.WorkoutClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;
    private final WorkoutClassRepository workoutClassRepository;

    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            MemberRepository memberRepository,
            WorkoutClassRepository workoutClassRepository
    ) {
        this.attendanceRepository = attendanceRepository;
        this.memberRepository = memberRepository;
        this.workoutClassRepository = workoutClassRepository;
    }

    @Override
    public AttendanceResponse register(AttendanceCreateRequest request) {

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new NotFoundException("Member not found: " + request.memberId()));

        WorkoutClass wc = workoutClassRepository.findById(request.classId())
                .orElseThrow(() -> new NotFoundException("WorkoutClass not found: " + request.classId()));

        // duplicate check (friendly before DB constraint)
        if (attendanceRepository.existsByMemberIdAndWorkoutClassId(member.getId(), wc.getId())) {
            throw new AlreadyRegisteredException("Member already registered for class: " + wc.getId());
        }

        long currentCount = attendanceRepository.countByWorkoutClassId(wc.getId());
        if (currentCount >= wc.getCapacity()) {
            throw new CapacityExceededException("Class is full (capacity=" + wc.getCapacity() + ")");
        }

        Attendance saved = attendanceRepository.save(
                Attendance.builder()
                        .member(member)
                        .workoutClass(wc)
                        .build()
        );

        return new AttendanceResponse(
                saved.getId(),
                saved.getMember().getId(),
                saved.getWorkoutClass().getId(),
                saved.getAttendedAt()
        );
    }
}
