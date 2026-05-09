package com.joaoczz.backend.persistence.repository;

import com.joaoczz.backend.persistence.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByUserId(Long userId);
    List<PostEntity> findByArtistId(Long artistId);
    List<PostEntity> findByGenreId(Long genreId);

    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"user", "artist", "genre"})
    java.util.Optional<PostEntity> findById(Long id);

    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"user", "artist", "genre"})
    Page<PostEntity> findAll(Pageable pageable);

    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"user", "artist", "genre"})
    @Query("SELECT p FROM PostEntity p WHERE " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.artist.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.genre.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<PostEntity> search(@Param("query") String query, Pageable pageable);
}
