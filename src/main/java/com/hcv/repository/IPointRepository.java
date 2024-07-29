package com.hcv.repository;

import com.hcv.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPointRepository extends JpaRepository<PointEntity, String> {
    boolean existsById(String id);

    PointEntity findOneById(String id);
}
