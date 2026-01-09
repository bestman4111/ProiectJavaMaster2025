package org.example.service;

import org.example.dto.request.BookCreateRequest;
import org.example.dto.response.BookCopyResponse;
import org.example.dto.response.BookResponse;

import java.util.List;

public interface CatalogService {
    BookResponse createBook(BookCreateRequest request);
    BookResponse getBook(Long id);
    BookCopyResponse addCopy(Long bookId);
    List<BookCopyResponse> listCopies(Long bookId);
}
