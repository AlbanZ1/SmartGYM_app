package com.smartgym.smartgym.controller;

import com.smartgym.smartgym.dto.request.MemberCreateRequest;
import com.smartgym.smartgym.dto.response.MemberResponse;
import com.smartgym.smartgym.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse create(@Valid @RequestBody MemberCreateRequest request) {
        return memberService.create(request);
    }

    @GetMapping("/{id}")
    public MemberResponse getById(@PathVariable Long id) {
        return memberService.getById(id);
    }

    // simple search/filter by name/email
    @GetMapping
    public List<MemberResponse> getAll(@RequestParam(required = false) String q) {
        return memberService.getAll(q);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        memberService.delete(id);
    }
}
