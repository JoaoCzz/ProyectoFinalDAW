package com.joaoczz.backend.presentation.dto.Category;

import java.util.List;

public record CategoryPageResponse(
        List<CategoryResponse> content,
        int page,
        int totalPages,
        long totalElements
) {
}
