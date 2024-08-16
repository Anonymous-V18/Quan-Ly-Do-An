package com.hcv.converter;

import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.response.TeacherDTO;
import com.hcv.dto.response.TeacherShowToSelectionResponse;
import com.hcv.entity.TeacherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ITeacherMapper {

    TeacherDTO toDTO(TeacherEntity teacherEntity);

    TeacherShowToSelectionResponse toShowDTOToSelection(TeacherEntity teacherEntity);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "researches", ignore = true)
    @Mapping(target = "jobs", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "departments", ignore = true)
    TeacherEntity toEntity(TeacherInput teacherInput);

}
