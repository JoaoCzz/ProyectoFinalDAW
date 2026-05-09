package com.joaoczz.backend.service.interfaces;

import com.joaoczz.backend.presentation.dto.genre.GenreRequest;
import com.joaoczz.backend.presentation.dto.genre.GenreResponse;

import java.util.List;

public interface IGenreService {
    GenreResponse create(GenreRequest request);
    GenreResponse getById(Long id);
    List<GenreResponse> getAll();
    GenreResponse update(Long id, GenreRequest request);
    void delete(Long id);
}
