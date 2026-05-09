package com.joaoczz.backend.persistence.repository;

import com.joaoczz.backend.persistence.entity.LikeCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeCommentEntity, Long> {
    List<LikeCommentEntity> findByCommentId(Long commentId);
    boolean existsByUserIdAndCommentId(Long userId, Long commentId);
    void deleteByUserIdAndCommentId(Long userId, Long commentId);
}
