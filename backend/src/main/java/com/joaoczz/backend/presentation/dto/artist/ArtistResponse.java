package com.joaoczz.backend.presentation.dto.artist;

import com.joaoczz.backend.persistence.entity.ArtistType;

public record ArtistResponse(
                Long id,
                String name,
                String slug,
                Boolean verified,
                ArtistType type) {
}
