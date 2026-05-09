// ===== ArtistRepository.java =====
package com.joaoczz.backend.persistence.repository;

import com.joaoczz.backend.persistence.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
    Optional<ArtistEntity> findBySlug(String slug);
    Optional<ArtistEntity> findByNameIgnoreCase(String name);
    boolean existsBySlug(String slug);
}
