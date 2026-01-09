package org.example.dto.response;

import java.time.Instant;

public record ReservationResponse (
    Long id,
    Long memberId,
    Long bookId,
    Instant createdAt
) {}
