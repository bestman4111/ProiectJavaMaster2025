package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.BookCreateRequest;
import org.example.dto.response.BookCopyResponse;
import org.example.dto.response.BookResponse;
import org.example.service.CatalogService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final CatalogService catalogService;

    @Operation(summary = "Create book")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse create(@Valid @RequestBody BookCreateRequest request) {
        return catalogService.createBook(request);
    }

    @Operation(summary = "Get book by id")
    @GetMapping("/{id}")
    public BookResponse get(@PathVariable Long id) {
        return catalogService.getBook(id);
    }

    @Operation(summary = "Add copy for a book")
    @PostMapping("/{bookId}/copies")
    @ResponseStatus(HttpStatus.CREATED)
    public BookCopyResponse addCopy(@PathVariable("bookId") Long bookId) {
        return catalogService.addCopy(bookId);
    }

    @Operation(summary = "List copies of a book")
    @GetMapping("/{bookId}/copies")
    public List<BookCopyResponse> getCopy(@PathVariable Long bookId) {
        return catalogService.listCopies(bookId);
    }
}
