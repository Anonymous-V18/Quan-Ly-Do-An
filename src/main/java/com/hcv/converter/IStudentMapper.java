package com.hcv.converter;

import com.hcv.dto.request.StudentInput;
import com.hcv.dto.response.StudentDTO;
import com.hcv.dto.response.StudentShowToSelectionResponse;
import com.hcv.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface IStudentMapper {

    StudentDTO toDTO(Student student);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "points", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "department", ignore = true)
    Student toEntity(StudentInput studentInput);

    StudentShowToSelectionResponse toShowToSelectionDTO(Student student);

    Student toEntity(@MappingTarget Student oldSubject, StudentInput subjectInput);

}
