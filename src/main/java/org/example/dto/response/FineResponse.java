package org.example.dto.response;

public record FineResponse (
    Long loanId,
    long overdueDays,
    double fineAmount
) {}
