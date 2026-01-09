package org.example.service;

import org.example.domain.Author;
import org.example.domain.Book;
import org.example.domain.Category;
import org.example.dto.request.BookCreateRequest;
import org.example.mapper.DtoMapper;
import org.example.repository.AuthorRepository;
import org.example.repository.BookCopyRepository;
import org.example.repository.BookRepository;
import org.example.repository.CategoryRepository;
import org.example.service.impl.CatalogServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CatalogServiceTest {
    @Test
    void createBook_shouldUpsertAuthorsAndCategories() {
        BookRepository bookRepo = mock(BookRepository.class);
        AuthorRepository authorRepo = mock(AuthorRepository.class);
        CategoryRepository categoryRepo = mock(CategoryRepository.class);
        BookCopyRepository copyRepo = mock(BookCopyRepository.class);

        CatalogService service = new CatalogServiceImpl(bookRepo, authorRepo, categoryRepo, copyRepo, new DtoMapper());

        var req = new BookCreateRequest(
                "Clean Code",
                "978-0132350884",
                List.of("Robert C. Martin"),
                List.of("Programming"));

        when(authorRepo.findByNameIgnoreCase("Robert C. Martin")).thenReturn(Optional.empty());
        when(authorRepo.save(any(Author.class))).thenAnswer(inv -> {
            Author a = inv.getArgument(0);
            a.setId(10L);
            return a;
        });

        when(categoryRepo.findByNameIgnoreCase("Programming")).thenReturn(Optional.empty());
        when(categoryRepo.save(any(Category.class))).thenAnswer(inv -> {
            Category c = inv.getArgument(0);
            c.setId(20L);
            return c;
        });

        when(bookRepo.save(any(Book.class))).thenAnswer(inv -> {
            Book b = inv.getArgument(0);
            b.setId(1L);
            return b;
        });

        var res = service.createBook(req);

        assertEquals(1L, res.id());
        assertEquals("9780132350884", res.isbn());
        assertTrue(res.authors().contains("Robert C. Martin"));
        assertTrue(res.categories().contains("Programming"));

        verify(authorRepo).save(any(Author.class));
        verify(categoryRepo).save(any(Category.class));
        verify(bookRepo).save(any(Book.class));
    }
}
