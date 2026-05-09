package com.joaoczz.backend.service.interfaces;

import com.joaoczz.backend.presentation.dto.like.LikeResponse;

public interface ILikeService {
    LikeResponse toggleLikePost(Long postId, String username);
    LikeResponse toggleLikeComment(Long commentId, String username);
}
