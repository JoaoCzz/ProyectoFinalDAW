package com.joaoczz.backend.presentation.dto.Category;

public record UpdateCategoryRequest(
        String name,
        String description,
        String image
) {
}
