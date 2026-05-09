package com.joaoczz.backend.service.implementation;

import com.joaoczz.backend.persistence.entity.CommentEntity;
import com.joaoczz.backend.persistence.entity.LikeCommentEntity;
import com.joaoczz.backend.persistence.entity.LikePostEntity;
import com.joaoczz.backend.persistence.entity.PostEntity;
import com.joaoczz.backend.persistence.entity.UserEntity;
import com.joaoczz.backend.persistence.repository.CommentRepository;
import com.joaoczz.backend.persistence.repository.LikeCommentRepository;
import com.joaoczz.backend.persistence.repository.LikePostRepository;
import com.joaoczz.backend.persistence.repository.PostRepository;
import com.joaoczz.backend.persistence.repository.UserRepository;
import com.joaoczz.backend.presentation.advice.ResourceNotFoundException;
import com.joaoczz.backend.presentation.dto.like.LikeResponse;
import com.joaoczz.backend.service.interfaces.ILikeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements ILikeService {

    @Autowired
    private LikePostRepository likePostRepository;
    @Autowired
    private LikeCommentRepository likeCommentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public LikeResponse toggleLikePost(Long postId, String username) {
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación no encontrada"));

        if (likePostRepository.existsByUserIdAndPostId(user.getId(), postId)) {
            likePostRepository.deleteByUserIdAndPostId(user.getId(), postId);
            int total = likePostRepository.findByPostId(postId).size();
            return new LikeResponse("Like eliminado", total, false);
        }

        LikePostEntity like = LikePostEntity.builder().user(user).post(post).build();
        likePostRepository.save(like);
        int total = likePostRepository.findByPostId(postId).size();
        return new LikeResponse("Like añadido", total, true);
    }

    @Override
    @Transactional
    public LikeResponse toggleLikeComment(Long commentId, String username) {
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado"));

        if (likeCommentRepository.existsByUserIdAndCommentId(user.getId(), commentId)) {
            likeCommentRepository.deleteByUserIdAndCommentId(user.getId(), commentId);
            int total = likeCommentRepository.findByCommentId(commentId).size();
            return new LikeResponse("Like eliminado", total, false);
        }

        LikeCommentEntity like = LikeCommentEntity.builder().user(user).comment(comment).build();
        likeCommentRepository.save(like);
        int total = likeCommentRepository.findByCommentId(commentId).size();
        return new LikeResponse("Like añadido", total, true);
    }
}
