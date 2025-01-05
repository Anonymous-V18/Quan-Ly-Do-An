package com.hcv.service.impl;

import com.hcv.entity.*;
import com.hcv.repository.IResearchTeacherRepository;
import com.hcv.service.IResearchTeacherService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResearchTeacherService implements IResearchTeacherService {

    IResearchTeacherRepository researchTeacherRepository;

    @Transactional
    @Override
    public void insert(List<Research> researches, Teacher teacher, TypeTeacher typeTeacher) {
        List<ResearchTeacher> researchTeachers = researches.stream().map(research -> {
            ResearchTeacher researchTeacher = new ResearchTeacher();
            researchTeacher.setId(new ResearchTeacherId(teacher.getId(), research.getId()));
            researchTeacher.setTypeTeacher(typeTeacher);
            researchTeacher.setTeacher(teacher);
            researchTeacher.setResearch(research);
            return researchTeacher;
        }).toList();
        researchTeacherRepository.saveAll(researchTeachers);
    }

    @Transactional
    @Override
    public void insert(Research research, List<Teacher> teacher, TypeTeacher typeTeacher) {
        List<ResearchTeacher> researchTeachers = teacher.stream().map(teacher1 -> {
            ResearchTeacher researchTeacher = new ResearchTeacher();
            researchTeacher.setId(new ResearchTeacherId(teacher1.getId(), research.getId()));
            researchTeacher.setTypeTeacher(typeTeacher);
            researchTeacher.setTeacher(teacher1);
            researchTeacher.setResearch(research);
            return researchTeacher;
        }).toList();
        researchTeacherRepository.saveAll(researchTeachers);
    }
}
