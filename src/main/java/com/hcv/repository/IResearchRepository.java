package com.hcv.repository;

import com.hcv.dto.StatusResearch;
import com.hcv.entity.Research;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface IResearchRepository extends JpaRepository<Research, String> {

    Page<Research> findByStatusInAndSubject_IdAndStageAndSchoolYear(Collection<StatusResearch> statuses,
                                                                    String subjectId, String stage,
                                                                    String schoolYear, Pageable pageable);

    long countByStatusInAndSubject_IdAndStageAndSchoolYear(Collection<StatusResearch> statuses, String subjectId,
                                                           String stage, String schoolYear);

    @Query("select r from Research r inner join r.researchTeachers researchTeachers " +
            "where researchTeachers.teacher.id = ?1 and researchTeachers.typeTeacher.code = ?2 " +
            "and r.schoolYear = ?3 and r.stage = ?4 and cast(r.status as string) like ?5 ")
    Page<Research> getAllCurrentResearch(String teacherId, String typeTeacherCode,
                                         String schoolYear, String stage, String status, Pageable pageable);

    @Query(" select count(r) from Research r inner join r.researchTeachers researchTeachers " +
            "where researchTeachers.teacher.id = ?1 and researchTeachers.typeTeacher.code = ?2 " +
            "and r.schoolYear = ?3 and r.stage = ?4 and cast(r.status as string) like ?5 ")
    long countByCurrentResearch(String teacherId, String typeTeacherCode, String schoolYear, String stage, String status);

}
