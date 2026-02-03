package com.smartgym.smartgym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgym.smartgym.dto.request.MemberCreateRequest;
import com.smartgym.smartgym.dto.response.MemberResponse;
import com.smartgym.smartgym.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private MemberService memberService;

    @Test
    void createMember_returns201_andResponseBody() throws Exception {
        MemberCreateRequest req = new MemberCreateRequest(
                "Vjosa", "Zulfija", "vjosa.controller@test.com", "070000000"
        );

        when(memberService.create(req)).thenReturn(
                new MemberResponse(
                        1L,
                        "Vjosa",
                        "Zulfija",
                        "vjosa.controller@test.com",
                        "070000000",
                        LocalDateTime.of(2026, 2, 3, 12, 0)
                )
        );

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Vjosa"))
                .andExpect(jsonPath("$.email").value("vjosa.controller@test.com"));
    }
}
