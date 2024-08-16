package com.hcv.repository;

import com.hcv.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISubjectRepository extends JpaRepository<SubjectEntity, String> {

    Optional<SubjectEntity> findByName(String name);

    boolean existsByName(String name);

    List<SubjectEntity> findByDepartments_Id(String id);


}
