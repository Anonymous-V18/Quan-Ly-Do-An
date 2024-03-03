package com.hcv.converter;

import com.hcv.dto.SubjectDTO;
import com.hcv.entity.SubjectEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ISubjectMapper {
    SubjectDTO toDTO(SubjectEntity subjectEntity);

    SubjectEntity toEntity(SubjectDTO subjectDTO);
}
