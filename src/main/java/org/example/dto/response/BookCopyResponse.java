package org.example.dto.response;

public record BookCopyResponse (
    Long id,
    Long bookId,
    String status
) {}
