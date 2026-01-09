package org.example.dto.response;

public record MemberResponse (
    Long id,
    String fullName,
    String email
) {}
