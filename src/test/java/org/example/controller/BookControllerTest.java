package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.request.BookCreateRequest;
import org.example.dto.response.BookCopyResponse;
import org.example.dto.response.BookResponse;
import org.example.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean
    CatalogService catalogService;

    @Test
    void createBook_shouldReturn201() throws Exception {
        var req = new BookCreateRequest(
                "Clean Code",
                "9780132350884",
                List.of("Robert C. Martin"),
                List.of("Programming")
        );

        var res = new BookResponse(
                1L,
                "Clean Code",
                "9780132350884",
                Set.of("Robert C. Martin"),
                Set.of("Programming")
        );

        when(catalogService.createBook(req)).thenReturn(res);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    void createBook_invalidIsbn_shouldReturn400() throws Exception {
        var req = new BookCreateRequest(
                "Bad ISBN",
                "ABC",
                List.of("Someone"),
                List.of("Programming")
        );

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addCopy_shouldReturn201() throws Exception {
        var copy = new BookCopyResponse(10L, 1L, "AVAILABLE");
        when(catalogService.addCopy(1L)).thenReturn(copy);

        mockMvc.perform(post("/api/books/1/copies"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }
}
