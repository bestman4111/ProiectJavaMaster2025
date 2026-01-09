package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.request.ReservationCreateRequest;
import org.example.dto.response.ReservationResponse;
import org.example.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean
    ReservationService reservationService;

    @Test
    void createReservation_shouldReturn201() throws Exception {
        var req = new ReservationCreateRequest(1L, 2L);
        var res = new ReservationResponse(100L, 1L, 2L, Instant.parse("2025-01-01T00:00:00Z"));

        when(reservationService.create(req)).thenReturn(res);

        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    void createReservation_missingMemberId_shouldReturn400() throws Exception {
        String body = """
                {"memberId":null,"bookId":2}
                """;

        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }
}
