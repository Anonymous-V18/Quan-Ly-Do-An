package com.hcv.converter;

import com.hcv.dto.request.StudentInput;
import com.hcv.dto.response.StudentDTO;
import com.hcv.dto.response.StudentShowToSelectionResponse;
import com.hcv.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IStudentMapper {

    StudentDTO toDTO(StudentEntity studentEntity);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "points", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "departments", ignore = true)
    StudentEntity toEntity(StudentInput studentInput);

    StudentShowToSelectionResponse toShowToSelectionDTO(StudentEntity studentEntity);

}
