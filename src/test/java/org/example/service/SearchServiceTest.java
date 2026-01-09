package org.example.service;

import org.example.domain.Book;
import org.example.domain.Category;
import org.example.mapper.DtoMapper;
import org.example.repository.BookRepository;
import org.example.service.impl.SearchServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchServiceTest {
    @Test
    void search_shouldFilterByCategory() {
        BookRepository bookRepo = mock(BookRepository.class);
        SearchService service = new SearchServiceImpl(bookRepo, new DtoMapper());

        Category programming = Category.builder().name("Programming").build();
        Category history = Category.builder().name("History").build();

        Book b1 = Book.builder().id(1L).title("Clean Code").isbn("9780132350884").categories(Set.of(programming)).build();
        Book b2 = Book.builder().id(2L).title("World War").isbn("1234567890").categories(Set.of(history)).build();

        when(bookRepo.findByTitleContainingIgnoreCase("c")).thenReturn(List.of(b1, b2));

        var res = service.search("c", "Programming");

        assertEquals(1, res.size());
        assertEquals(1L, res.get(0).id());
    }
}
