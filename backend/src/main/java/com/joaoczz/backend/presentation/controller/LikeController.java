package com.joaoczz.backend.presentation.controller;

import com.joaoczz.backend.presentation.dto.like.LikeResponse;
import com.joaoczz.backend.service.interfaces.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private ILikeService likeService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<LikeResponse> toggleLikePost(
            @PathVariable Long postId,
            Authentication authentication) {
        return ResponseEntity.ok(likeService.toggleLikePost(postId, authentication.getName()));
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<LikeResponse> toggleLikeComment(
            @PathVariable Long commentId,
            Authentication authentication) {
        return ResponseEntity.ok(likeService.toggleLikeComment(commentId, authentication.getName()));
    }
}
