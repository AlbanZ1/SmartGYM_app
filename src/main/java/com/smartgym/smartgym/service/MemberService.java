package com.smartgym.smartgym.service;

import com.smartgym.smartgym.dto.request.MemberCreateRequest;
import com.smartgym.smartgym.dto.response.MemberResponse;

import java.util.List;

public interface MemberService {
    MemberResponse create(MemberCreateRequest request);
    MemberResponse getById(Long id);
    List<MemberResponse> getAll(String q);
    void delete(Long id);
}
