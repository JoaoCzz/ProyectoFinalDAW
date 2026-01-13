package com.joaoczz.backend.service.implementation;

import com.joaoczz.backend.persistence.entity.CategoryEntity;
import com.joaoczz.backend.persistence.repository.CategoryRepository;
import com.joaoczz.backend.presentation.advice.ResourceNotFoundException;
import com.joaoczz.backend.presentation.dto.Category.CategoryPageResponse;
import com.joaoczz.backend.presentation.dto.Category.CategoryResponse;
import com.joaoczz.backend.presentation.dto.Category.CreateCategoryRequest;
import com.joaoczz.backend.presentation.dto.Category.UpdateCategoryRequest;
import com.joaoczz.backend.service.interfaces.CategoryService;
import com.joaoczz.backend.service.mapper.CategoryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceimpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;


    @Override
    public CategoryResponse getById(Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
        return mapper.CategoryEntityToResponse(entity);
    }

    @Override
    public CategoryResponse create(CreateCategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Category name already exists");
        }
        CategoryEntity entity = mapper.CreateRequestToCategoryEntity(request);
        CategoryEntity saved = categoryRepository.save(entity);
        return mapper.CategoryEntityToResponse(saved);
    }

    @Override
    public CategoryResponse update(Long id, UpdateCategoryRequest request) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));

        mapper.updateEntityFromRequest(request, entity);

        CategoryEntity updated = categoryRepository.save(entity);
        return mapper.CategoryEntityToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id " + id);
        }
        categoryRepository.deleteById(id);
    }

/**
  * Obtiene una página de categorías.
  *
  * Aplica paginación para no cargar todos los registros y convierte
  * las entidades a DTOs para la respuesta.
 */

    @Override
    public CategoryPageResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Configura paginación
        Page<CategoryResponse> categoryPage = categoryRepository.findAll(pageable)
                .map(mapper::CategoryEntityToResponse); // Mapea entidades a DTOs

        return new CategoryPageResponse(
                categoryPage.getContent(),      // Datos de la página
                categoryPage.getNumber(),       // Número de página
                categoryPage.getTotalPages(),   // Total de páginas
                categoryPage.getTotalElements() // Total de elementos
        );
    }
}
