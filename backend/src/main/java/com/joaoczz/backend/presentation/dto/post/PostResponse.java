package com.joaoczz.backend.presentation.dto.post;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String description,
        String musicUrl,
        boolean isSelfPromotion,
        LocalDateTime createdAt,
        Long userId,
        String username,
        Long artistId,
        String artistName,
        Long genreId,
        String genreName,
        int totalLikes,
        int totalComments
) {}
