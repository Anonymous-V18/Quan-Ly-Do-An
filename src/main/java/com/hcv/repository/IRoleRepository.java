package com.hcv.repository;

import com.hcv.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<RoleEntity, String> {

    Optional<RoleEntity> findByName(String roleName);

}
