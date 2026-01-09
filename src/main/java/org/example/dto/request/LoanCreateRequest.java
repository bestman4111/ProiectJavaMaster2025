package org.example.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoanCreateRequest (
    @NotNull Long memberId,
    @NotNull Long copyId
) {}
