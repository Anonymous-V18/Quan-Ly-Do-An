package com.hcv.converter;

import com.hcv.dto.StudentDTO;
import com.hcv.dto.request.student.StudentInput;
import com.hcv.entity.StudentEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IStudentMapper {

    StudentEntity toEntity(StudentDTO studentDTO);

    StudentDTO toDTO(StudentEntity studentEntity);

    StudentEntity toEntity(StudentInput studentInput);

}
