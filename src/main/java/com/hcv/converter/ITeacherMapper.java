package com.hcv.converter;

import com.hcv.dto.TeacherDTO;
import com.hcv.dto.request.TeacherInput;
import com.hcv.entity.TeacherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface ITeacherMapper {
    TeacherDTO toDTO(TeacherEntity teacherEntity);

    TeacherDTO toDTO(TeacherInput teacherInput);

    TeacherDTO toDTO(@MappingTarget TeacherDTO old_teacherDTO, TeacherInput teacherInput);


    TeacherEntity toEntity(TeacherDTO teacherDTO);

    TeacherEntity toEntity(TeacherInput teacherInput);

}
