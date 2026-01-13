package com.joaoczz.backend.presentation.dto.Category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotBlank
        String image
) {
}
