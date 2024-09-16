package com.hcv.repository;

import com.hcv.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, String> {

    List<RoleEntity> findByNameIn(Collection<String> names);

}
