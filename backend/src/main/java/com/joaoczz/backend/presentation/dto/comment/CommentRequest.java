// ===== CommentRequest.java =====
package com.joaoczz.backend.presentation.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
        @NotBlank String description,
        @NotNull Long postId
) {}
