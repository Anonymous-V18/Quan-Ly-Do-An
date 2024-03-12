package com.hcv.converter;

import com.hcv.dto.TeacherDTO;
import com.hcv.dto.request.TeacherInput;
import com.hcv.entity.TeacherEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ITeacherMapper {
    TeacherDTO toDTO(TeacherEntity teacherEntity);

    TeacherEntity toEntity(TeacherDTO teacherDTO);

    TeacherDTO toDTO(TeacherInput teacherInput);

    TeacherEntity toEntity(TeacherInput teacherInput);
}
