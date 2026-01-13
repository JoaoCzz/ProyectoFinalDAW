package com.joaoczz.backend.persistence.repository;

import com.joaoczz.backend.persistence.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleRepository extends CrudRepository <RoleEntity,Long> {
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List <String> roleNames);
}
