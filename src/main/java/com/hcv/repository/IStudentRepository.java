package com.hcv.repository;

import com.hcv.entity.Student;
import com.hcv.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findByCode(String code);

    Optional<Student> findByCodeAndIdNot(String code, String id);

    List<Student> findByGroups_IdIn(List<String> ids);

    @Query(value = "select s from Student s where s.id <> :id " +
            "and s.subjects in :subjects and s.users.isGraduate = :isGraduate " +
            "and s.groups is null")
    List<Student> findStudentToInvite(String id, Collection<Subject> subjects, Integer isGraduate);

}
