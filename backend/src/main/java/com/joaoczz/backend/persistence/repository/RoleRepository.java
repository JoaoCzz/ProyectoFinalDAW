package com.joaoczz.backend.persistence.repository;

import com.joaoczz.backend.persistence.entity.RoleEntity;
import com.joaoczz.backend.persistence.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Set<RoleEntity> findRoleEntitiesByRoleEnumIn(Collection<RoleEnum> roleEnums);
}
