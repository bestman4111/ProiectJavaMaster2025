package org.example.dto.request;

import jakarta.validation.constraints.NotNull;

public record BookCopyCreateRequest (
    @NotNull Long bookId
) {}
