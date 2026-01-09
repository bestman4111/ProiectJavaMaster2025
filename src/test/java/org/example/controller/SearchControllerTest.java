package org.example.controller;

import org.example.dto.response.BookResponse;
import org.example.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
class SearchControllerTest {
    @Autowired MockMvc mockMvc;

    @MockitoBean
    SearchService searchService;

    @Test
    void search_shouldReturn200() throws Exception {
        var res = List.of(
                new BookResponse(
                        1L,
                        "Clean Code",
                        "9780132350884",
                        Set.of("Robert C. Martin"),
                        Set.of("Programming")
                )
        );

        when(searchService.search("clean", null)).thenReturn(res);

        mockMvc.perform(get("/api/books/search")
                .param("q", "clean"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Clean Code"));
    }
}
