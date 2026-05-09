package com.joaoczz.backend.service.implementation;

import com.joaoczz.backend.persistence.entity.CommentEntity;
import com.joaoczz.backend.persistence.entity.PostEntity;
import com.joaoczz.backend.persistence.entity.UserEntity;
import com.joaoczz.backend.persistence.repository.CommentRepository;
import com.joaoczz.backend.persistence.repository.LikeCommentRepository;
import com.joaoczz.backend.persistence.repository.PostRepository;
import com.joaoczz.backend.persistence.repository.UserRepository;
import com.joaoczz.backend.presentation.advice.ResourceNotFoundException;
import com.joaoczz.backend.presentation.dto.comment.CommentRequest;
import com.joaoczz.backend.presentation.dto.comment.CommentResponse;
import com.joaoczz.backend.service.interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @Override
    public CommentResponse create(CommentRequest request, String username) {
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        PostEntity post = postRepository.findById(request.postId())
                .orElseThrow(() -> new ResourceNotFoundException("Publicación no encontrada con id: " + request.postId()));

        CommentEntity comment = CommentEntity.builder()
                .description(request.description())
                .user(user)
                .post(post)
                .createdAt(LocalDateTime.now())
                .build();

        return toResponse(commentRepository.save(comment), username);
    }

    @Override
    public Page<CommentResponse> getByPost(Long postId, Pageable pageable, String username) {
        return commentRepository.findByPostId(postId, pageable).map(comment -> toResponse(comment, username));
    }

    @Override
    public void delete(Long id, String username) {
        CommentEntity comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado con id: " + id));
        if (!comment.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("No tienes permiso para eliminar este comentario");
        }
        commentRepository.deleteById(id);
    }

    private CommentResponse toResponse(CommentEntity comment, String username) {
        boolean likedByCurrentUser = false;
        if (username != null && comment.getId() != null) {
            likedByCurrentUser = userRepository.findUserEntityByUsername(username)
                    .map(user -> likeCommentRepository.existsByUserIdAndCommentId(user.getId(), comment.getId()))
                    .orElse(false);
        }

        int totalLikes = comment.getLikes() != null ? comment.getLikes().size() : 0;

        return new CommentResponse(
                comment.getId(),
                comment.getDescription(),
                comment.getCreatedAt(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getPost().getId(),
                totalLikes,
                likedByCurrentUser
        );
    }
}
