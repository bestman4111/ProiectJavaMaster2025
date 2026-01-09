package org.example.service;

import org.example.dto.response.BookResponse;

import java.util.List;

public interface SearchService {
    List<BookResponse> search(String q, String category);
}
