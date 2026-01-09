package org.example.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest (
    @NotBlank String fullName,
    @NotBlank @Email String email
) {}
