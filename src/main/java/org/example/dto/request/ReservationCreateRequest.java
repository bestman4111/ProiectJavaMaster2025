package org.example.dto.request;

import jakarta.validation.constraints.NotNull;

public record ReservationCreateRequest (
    @NotNull Long memberId,
    @NotNull Long bookId
) {}
