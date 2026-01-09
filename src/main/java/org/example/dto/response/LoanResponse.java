package org.example.dto.response;

import java.time.LocalDate;

public record LoanResponse (
    Long id,
    Long memberId,
    Long copyId,
    LocalDate startDate,
    LocalDate dueDate,
    LocalDate returnDate
) {}
