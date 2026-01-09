package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.dto.response.BookResponse;
import org.example.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @Operation(summary = "Search books", description = "Search by title (q) and optional category filter")
    @GetMapping("/search")
    public List<BookResponse> search(@RequestParam(name = "q", required = false) String q,
                                     @RequestParam(name = "category", required = false) String category) {
        return searchService.search(q, category);
    }
}
