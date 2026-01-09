package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.request.MemberCreateRequest;
import org.example.dto.response.MemberResponse;
import org.example.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean MemberService memberService;

    @Test
    void createMember_shouldReturn201() throws Exception {
        var req = new MemberCreateRequest("John Doe", "john@example.com");
        when(memberService.create(req)).thenReturn(new MemberResponse(1L, "John Doe", "john@example.com"));

        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void createMember_invalidEmail_shouldReturn400() throws Exception {
        var req = new MemberCreateRequest("John Doe", "not-an-email");

        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
}
