package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.domain.Book;
import org.example.dto.response.BookResponse;
import org.example.mapper.DtoMapper;
import org.example.repository.BookRepository;
import org.example.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final BookRepository bookRepository;
    private final DtoMapper mapper;

    @Override
    public List<BookResponse> search(String q, String category) {
        String query = q == null ? "" : q.trim();
        List<Book> byTitle = bookRepository.findByTitleContainingIgnoreCase(query);

        if(category == null || category.isBlank()) {
            return byTitle.stream().map(mapper::toBookResponse).toList();
        }

        String cat = category.trim().toLowerCase();
        return byTitle.stream()
                .filter(b -> b.getCategories().stream().anyMatch(c -> c.getName().toLowerCase().equals(cat)))
                .map(mapper::toBookResponse)
                .toList();
    }
}
