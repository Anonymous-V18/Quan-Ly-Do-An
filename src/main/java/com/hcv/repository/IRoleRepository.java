package com.hcv.repository;

import com.hcv.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, String> {

    RoleEntity findOneByName(String roleNames);

}
