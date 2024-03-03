package com.hcv.converter;

import com.hcv.dto.JobDTO;
import com.hcv.dto.input.JobForTeacherInput;
import com.hcv.entity.JobEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IJobMapper {
    JobDTO toDTO(JobEntity jobEntity);

    JobEntity toEntity(JobDTO jobDTO);

    JobEntity toEntity(JobForTeacherInput jobForTeacherInput);

    JobDTO toDTO(JobForTeacherInput jobForTeacherInput);
}
