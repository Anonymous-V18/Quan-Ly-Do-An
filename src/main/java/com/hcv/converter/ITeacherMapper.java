package com.hcv.converter;

import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.response.TeacherDTO;
import com.hcv.dto.response.TeacherShowToSelectionResponse;
import com.hcv.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ITeacherMapper {

    TeacherDTO toDTO(Teacher teacher);

    TeacherShowToSelectionResponse toShowDTOToSelection(Teacher teacher);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "researches", ignore = true)
    @Mapping(target = "jobs", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "departments", ignore = true)
    Teacher toEntity(TeacherInput teacherInput);

    @Mapping(target = "position", expression = "java(teacherInput.getPosition())")
    Teacher toEntity(@MappingTarget Teacher oldTeacher, TeacherInput teacherInput);

}
