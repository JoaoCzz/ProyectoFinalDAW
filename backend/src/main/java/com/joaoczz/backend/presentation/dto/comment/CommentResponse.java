package com.joaoczz.backend.presentation.dto.comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String description,
        LocalDateTime createdAt,
        Long userId,
        String username,
        Long postId,
        int totalLikes,
        boolean likedByCurrentUser
) {}
