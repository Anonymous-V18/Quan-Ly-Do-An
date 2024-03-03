package com.hcv.repository;

import com.hcv.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    DepartmentEntity findOneById(Long id);

    DepartmentEntity findOneByName(String name);
}
