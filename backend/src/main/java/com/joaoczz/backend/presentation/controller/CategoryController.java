package com.joaoczz.backend.presentation.controller;

import com.joaoczz.backend.presentation.dto.Category.CategoryPageResponse;
import com.joaoczz.backend.presentation.dto.Category.CategoryResponse;
import com.joaoczz.backend.presentation.dto.Category.CreateCategoryRequest;
import com.joaoczz.backend.service.interfaces.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/categories")
@PreAuthorize("denyAll()")
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<CategoryResponse> create(
            @Valid @RequestBody CreateCategoryRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        CategoryResponse response = categoryService.create(request);
        URI location = uriBuilder.path("/api/categories/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CategoryPageResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var categoryPage = categoryService.getAll(page, size);
        return ResponseEntity.ok(categoryPage);
    }
}
