package com.hcv.repository;

import com.hcv.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartmentRepository extends JpaRepository<DepartmentEntity, String> {

    DepartmentEntity findOneById(String id);

    DepartmentEntity findOneByName(String name);
}
