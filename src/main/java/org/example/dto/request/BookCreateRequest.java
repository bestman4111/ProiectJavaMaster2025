package org.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.validation.ValidIsbn;

import java.util.List;

public record BookCreateRequest (
    @NotBlank String title,
    @NotBlank @ValidIsbn String isbn,
    @Size(min = 1) List<@NotBlank String> authors,
    List<@NotBlank String> categories
) {}
