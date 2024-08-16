package com.hcv.repository;

import com.hcv.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ITeacherRepository extends JpaRepository<TeacherEntity, String> {

    Optional<TeacherEntity> findByCode(String code);

    boolean existsByCode(String maSo);

    boolean existsById(String id);

    boolean existsByIdIn(Collection<String> ids);

}
