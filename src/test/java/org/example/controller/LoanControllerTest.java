package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.request.LoanCreateRequest;
import org.example.dto.response.LoanResponse;
import org.example.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean LoanService loanService;

    @Test
    void createLoan_shouldReturn201() throws Exception {
        var req = new LoanCreateRequest(1L, 10L);
        var res = new LoanResponse(100L, 1L, 10L, LocalDate.now(), LocalDate.now().plusDays(14), null);

        when(loanService.createLoan(req)).thenReturn(res);

        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100));
    }
}
