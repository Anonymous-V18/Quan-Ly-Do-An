package com.hcv.repository;

import com.hcv.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubjectRepository extends JpaRepository<SubjectEntity, String> {
    SubjectEntity findOneByName(String name);

    SubjectEntity findOneById(String id);

    boolean existsByName(String name);

}
