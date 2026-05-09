// ===== PostRequest.java =====
package com.joaoczz.backend.presentation.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRequest(
        @NotBlank String title,
        String description,
        String musicUrl,
        boolean isSelfPromotion,
        Long artistId,
        @NotNull Long genreId
) {}
