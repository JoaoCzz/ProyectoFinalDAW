package com.joaoczz.backend.service.implementation;

import com.joaoczz.backend.persistence.entity.GenreEntity;
import com.joaoczz.backend.persistence.repository.GenreRepository;
import com.joaoczz.backend.presentation.advice.ResourceNotFoundException;
import com.joaoczz.backend.presentation.dto.genre.GenreRequest;
import com.joaoczz.backend.presentation.dto.genre.GenreResponse;
import com.joaoczz.backend.service.interfaces.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements IGenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public GenreResponse create(GenreRequest request) {
        if (genreRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Ya existe un género con ese nombre");
        }
        GenreEntity entity = GenreEntity.builder().name(request.name()).build();
        return toResponse(genreRepository.save(entity));
    }

    @Override
    public GenreResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    public List<GenreResponse> getAll() {
        return genreRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public GenreResponse update(Long id, GenreRequest request) {
        GenreEntity entity = findOrThrow(id);
        entity.setName(request.name());
        return toResponse(genreRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        findOrThrow(id);
        genreRepository.deleteById(id);
    }

    private GenreEntity findOrThrow(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Género no encontrado con id: " + id));
    }

    private GenreResponse toResponse(GenreEntity entity) {
        return new GenreResponse(entity.getId(), entity.getName());
    }
}
