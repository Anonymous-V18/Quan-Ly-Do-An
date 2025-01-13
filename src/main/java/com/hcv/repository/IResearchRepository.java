package com.hcv.repository;

import com.hcv.dto.StatusResearch;
import com.hcv.entity.Research;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface IResearchRepository extends JpaRepository<Research, String> {

    Page<Research> findByStatusInAndSubject_Id(Collection<StatusResearch> statuses, String id, Pageable pageable);

    long countByStatusInAndSubject_Id(Collection<StatusResearch> statuses, String id);

    long countByResearchTeachers_Teacher_Id(String id);

    @Query("""
            select r from Research r inner join r.researchTeachers researchTeachers
            where researchTeachers.teacher.id = ?1 and researchTeachers.typeTeacher.code = ?2 and r.schoolYear = ?3 and r.stage = ?4""")
    Page<Research> getAllCurrentResearch(String teacherId, String typeTeacherCode, String schoolYear, String stage, Pageable pageable);

    @Query("""
            select count(r) from Research r inner join r.researchTeachers researchTeachers
            where researchTeachers.teacher.id = ?1 and researchTeachers.typeTeacher.code = ?2 and r.schoolYear = ?3 and r.stage = ?4""")
    long countByCurrentResearch(String teacherId, String typeTeacherCode, String schoolYear, String stage);

}
