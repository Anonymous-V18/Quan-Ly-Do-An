package com.hcv.converter;

import com.hcv.dto.TeacherDTO;
import com.hcv.dto.request.teacher.TeacherInput;
import com.hcv.entity.TeacherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ITeacherMapper {
    TeacherDTO toDTO(TeacherEntity teacherEntity);

    TeacherDTO toDTO(TeacherInput teacherInput);

    @Mapping(target = "chucVu", ignore = true)
    TeacherDTO toDTO(@MappingTarget TeacherDTO oldTeacherDTO, TeacherInput teacherInput);


    TeacherEntity toEntity(TeacherDTO teacherDTO);

    TeacherEntity toEntity(TeacherInput teacherInput);

}
