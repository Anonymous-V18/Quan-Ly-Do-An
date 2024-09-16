package com.hcv.repository;

import com.hcv.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPointRepository extends JpaRepository<Point, String> {

    boolean existsById(String id);

}
