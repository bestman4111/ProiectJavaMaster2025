package org.example.dto.response;

import java.util.Set;

public record BookResponse (
    Long id,
    String title,
    String isbn,
    Set<String> authors,
    Set<String> categories
) {}
