package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.MemberCreateRequest;
import com.smartgym.smartgym.dto.response.MemberResponse;
import com.smartgym.smartgym.entity.Member;
import com.smartgym.smartgym.exception.BadRequestException;
import com.smartgym.smartgym.exception.NotFoundException;
import com.smartgym.smartgym.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberResponse create(MemberCreateRequest request) {
        if (memberRepository.existsByEmailIgnoreCase(request.email())) {
            throw new BadRequestException("Email already exists: " + request.email());
        }

        Member member = Member.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .build();

        Member saved = memberRepository.save(member);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getById(Long id) {
        Member m = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Member not found: " + id));
        return toResponse(m);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberResponse> getAll(String q) {
        List<Member> list = memberRepository.findAll();

        if (q == null || q.isBlank()) {
            return list.stream().map(this::toResponse).toList();
        }

        String query = q.toLowerCase();

        return list.stream()
                .filter(m ->
                        (m.getFirstName() != null && m.getFirstName().toLowerCase().contains(query)) ||
                                (m.getLastName() != null && m.getLastName().toLowerCase().contains(query)) ||
                                (m.getEmail() != null && m.getEmail().toLowerCase().contains(query))
                )
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new NotFoundException("Member not found: " + id);
        }
        memberRepository.deleteById(id);
    }

    private MemberResponse toResponse(Member m) {
        return new MemberResponse(
                m.getId(),
                m.getFirstName(),
                m.getLastName(),
                m.getEmail(),
                m.getPhone(),
                m.getCreatedAt()
        );
    }
}
