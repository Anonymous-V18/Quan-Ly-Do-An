package com.hcv.repository;

import com.hcv.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupRepository extends JpaRepository<Group, String> {

    Page<Group> findByResearch_Teachers_Id(String id, Pageable pageable);

    long countByResearch_Teachers_Id(String id);

    @Query("""
              select count(distinct g) from Group g inner join g.students students
              where students.user.isGraduate = ?1 and students.subject.id = ?2
            """)
    long countGroupHaveTheSameSubject(Integer isGraduate, String id);


}
