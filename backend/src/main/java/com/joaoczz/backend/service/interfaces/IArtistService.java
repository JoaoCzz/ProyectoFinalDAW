package com.joaoczz.backend.service.interfaces;

import com.joaoczz.backend.presentation.dto.artist.ArtistRequest;
import com.joaoczz.backend.presentation.dto.artist.ArtistResponse;

import java.util.List;

public interface IArtistService {
    ArtistResponse create(ArtistRequest request);
    ArtistResponse getById(Long id);
    List<ArtistResponse> getAll();
    ArtistResponse update(Long id, ArtistRequest request);
    void delete(Long id);
}
