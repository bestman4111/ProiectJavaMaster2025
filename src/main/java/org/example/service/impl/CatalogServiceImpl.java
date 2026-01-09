package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domain.Author;
import org.example.domain.Book;
import org.example.domain.BookCopy;
import org.example.domain.Category;
import org.example.dto.request.BookCreateRequest;
import org.example.dto.response.BookCopyResponse;
import org.example.dto.response.BookResponse;
import org.example.exception.NotFoundException;
import org.example.mapper.DtoMapper;
import org.example.repository.AuthorRepository;
import org.example.repository.BookCopyRepository;
import org.example.repository.BookRepository;
import org.example.repository.CategoryRepository;
import org.example.service.CatalogService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookCopyRepository copyRepository;
    private final DtoMapper mapper;

    @Override
    @Transactional
    public BookResponse createBook(BookCreateRequest request) {
        Set<Author> authors = new HashSet<>();
        for(String name : request.authors()) {
            Author a = authorRepository.findByNameIgnoreCase(name)
                    .orElseGet(() -> authorRepository.save(Author.builder().name(name).build()));
            authors.add(a);
        }

        Set<Category> categories = new HashSet<>();
        if(request.categories() != null) {
            for(String name : request.categories()) {
                Category c = categoryRepository.findByNameIgnoreCase(name)
                        .orElseGet(() -> categoryRepository.save(Category.builder().name(name).build()));
                categories.add(c);
            }
        }

        Book b = Book.builder()
                .title(request.title())
                .isbn(request.isbn().replace("-", "").trim())
                .authors(authors)
                .categories(categories)
                .build();

        return mapper.toBookResponse(bookRepository.save(b));
    }

    @Override
    public BookResponse getBook(Long id) {
        Book b = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found: " + id));
        return mapper.toBookResponse(b);
    }

    @Override
    @Transactional
    public BookCopyResponse addCopy(Long bookId) {
        Book b = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found: " + bookId));

        BookCopy copy = BookCopy.builder()
                .book(b)
                .status(BookCopy.Status.AVAILABLE)
                .build();

        return mapper.toCopyResponse(copyRepository.save(copy));
    }

    @Override
    public List<BookCopyResponse> listCopies(Long bookId) {
        return copyRepository.findByBookId(bookId).stream().map(mapper::toCopyResponse).toList();
    }
}
