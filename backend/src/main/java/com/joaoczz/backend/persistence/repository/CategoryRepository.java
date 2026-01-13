package com.joaoczz.backend.persistence.repository;

import com.joaoczz.backend.persistence.entity.CategoryEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    boolean existsByName(@NotBlank String name);
}
