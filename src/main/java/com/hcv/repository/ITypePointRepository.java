package com.hcv.repository;

import com.hcv.entity.TypePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITypePointRepository extends JpaRepository<TypePoint, String> {
}
