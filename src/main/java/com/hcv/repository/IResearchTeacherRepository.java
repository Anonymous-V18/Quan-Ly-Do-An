package com.hcv.repository;

import com.hcv.entity.ResearchTeacher;
import com.hcv.entity.ResearchTeacherId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IResearchTeacherRepository extends JpaRepository<ResearchTeacher, ResearchTeacherId> {

    @Query("""
            select (count(r) > 0) from ResearchTeacher r
            where r.teacher.id = ?1 and r.research.id = ?2 and r.typeTeacher.code = ?3""")
    boolean isTeacherValidWithTypeTeacherInResearch(String teacherId, String researchId, String code);

    Optional<ResearchTeacher> findByTypeTeacher_CodeAndResearch_Id(String typeTeacherCode, String researchId);

}