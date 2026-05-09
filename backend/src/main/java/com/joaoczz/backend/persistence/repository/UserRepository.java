package com.joaoczz.backend.persistence.repository;

import com.joaoczz.backend.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository <UserEntity,Long> {
    List<UserEntity> findAll();
    Optional<UserEntity> findUserEntityByUsername(String username);
}
