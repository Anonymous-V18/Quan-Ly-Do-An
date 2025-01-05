package com.hcv.service;

import com.hcv.entity.Research;
import com.hcv.entity.Teacher;
import com.hcv.entity.TypeTeacher;

import java.util.List;

public interface IResearchTeacherService {

    void insert(List<Research> researches, Teacher teacher, TypeTeacher typeTeacher);

    void insert(Research research, List<Teacher> teacher, TypeTeacher typeTeacher);

}
