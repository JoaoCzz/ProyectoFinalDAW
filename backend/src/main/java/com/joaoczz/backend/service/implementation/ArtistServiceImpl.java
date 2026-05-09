package com.joaoczz.backend.service.implementation;

import com.joaoczz.backend.persistence.entity.ArtistEntity;
import com.joaoczz.backend.persistence.entity.ArtistType;
import com.joaoczz.backend.persistence.repository.ArtistRepository;
import com.joaoczz.backend.presentation.advice.ResourceNotFoundException;
import com.joaoczz.backend.presentation.dto.artist.ArtistRequest;
import com.joaoczz.backend.presentation.dto.artist.ArtistResponse;
import com.joaoczz.backend.service.interfaces.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements IArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public ArtistResponse create(ArtistRequest request) {
        String slug = request.slug() != null && !request.slug().isBlank() ? request.slug() : toSlug(request.name());
        if (artistRepository.existsBySlug(slug)) {
            throw new IllegalArgumentException("Ya existe un artista con ese slug");
        }
        ArtistEntity entity = ArtistEntity.builder()
                .name(request.name())
                .slug(slug)
                .verified(request.verified() != null ? request.verified() : false)
                .type(request.type() != null ? request.type() : ArtistType.SOLO)
                .build();
        return toResponse(artistRepository.save(entity));
    }

    @Override
    public ArtistResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    public List<ArtistResponse> getAll() {
        return artistRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistResponse update(Long id, ArtistRequest request) {
        ArtistEntity entity = findOrThrow(id);
        entity.setName(request.name());
        if (request.slug() != null && !request.slug().isBlank()) {
            entity.setSlug(request.slug());
        }
        if (request.verified() != null) {
            entity.setVerified(request.verified());
        }
        if (request.type() != null) {
            entity.setType(request.type());
        }
        return toResponse(artistRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        findOrThrow(id);
        artistRepository.deleteById(id);
    }

    private ArtistEntity findOrThrow(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + id));
    }

    private ArtistResponse toResponse(ArtistEntity entity) {
        return new ArtistResponse(entity.getId(), entity.getName(), entity.getSlug(), entity.getVerified(),
                entity.getType());
    }

    private String toSlug(String input) {
        if (input == null)
            return null;
        String slug = input.trim().toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9-]", "");
        return slug;
    }
}
