package com.hcv.converter;

import com.hcv.dto.StudentDTO;
import com.hcv.dto.request.StudentInput;
import com.hcv.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface IStudentMapper {
    StudentEntity toEntity(StudentDTO studentDTO);

    StudentDTO toDTO(StudentEntity studentEntity);

    StudentDTO toDTO(StudentInput studentInput);

    StudentEntity toEntity(StudentInput studentInput);

    StudentDTO toDTO(@MappingTarget StudentDTO old_studentDTO, StudentInput studentInput);
}
