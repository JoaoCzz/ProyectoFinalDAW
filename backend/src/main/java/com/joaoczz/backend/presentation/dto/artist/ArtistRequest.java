// ===== ArtistRequest.java =====
package com.joaoczz.backend.presentation.dto.artist;

import com.joaoczz.backend.persistence.entity.ArtistType;
import jakarta.validation.constraints.NotBlank;

public record ArtistRequest(
                @NotBlank String name,
                String slug,
                Boolean verified,
                ArtistType type) {
}
