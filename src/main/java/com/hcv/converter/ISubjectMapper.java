package com.hcv.converter;

import com.hcv.dto.request.SubjectInput;
import com.hcv.dto.response.SubjectDTO;
import com.hcv.dto.response.SubjectResponse;
import com.hcv.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ISubjectMapper {

    SubjectDTO toDTO(Subject subject);

    SubjectResponse toShowDTO(Subject subject);

    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "researches", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    Subject toEntity(SubjectInput subjectInput);

}
