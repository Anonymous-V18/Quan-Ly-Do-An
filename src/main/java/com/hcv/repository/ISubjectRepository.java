package com.hcv.repository;

import com.hcv.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject, String> {

    Optional<Subject> findByName(String name);

    List<Subject> findByDepartment_Id(String id);

}
