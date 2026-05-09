package com.joaoczz.backend.service.interfaces;

import com.joaoczz.backend.presentation.dto.comment.CommentRequest;
import com.joaoczz.backend.presentation.dto.comment.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommentService {
    CommentResponse create(CommentRequest request, String username);
    Page<CommentResponse> getByPost(Long postId, Pageable pageable);
    void delete(Long id, String username);
}
