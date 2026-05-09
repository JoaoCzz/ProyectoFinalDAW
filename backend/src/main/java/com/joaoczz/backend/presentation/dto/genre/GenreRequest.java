// ===== GenreRequest.java =====
package com.joaoczz.backend.presentation.dto.genre;

import jakarta.validation.constraints.NotBlank;

public record GenreRequest(
        @NotBlank String name
) {}
