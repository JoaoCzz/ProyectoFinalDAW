package com.joaoczz.backend.service.interfaces;

import com.joaoczz.backend.presentation.dto.Category.CategoryPageResponse;
import com.joaoczz.backend.presentation.dto.Category.CategoryResponse;
import com.joaoczz.backend.presentation.dto.Category.CreateCategoryRequest;
import com.joaoczz.backend.presentation.dto.Category.UpdateCategoryRequest;


public interface CategoryService {
    CategoryResponse getById(Long id);
    CategoryResponse create(CreateCategoryRequest request);
    CategoryResponse update(Long id, UpdateCategoryRequest request);
    void delete(Long id);
    CategoryPageResponse getAll(int page, int size);
}
