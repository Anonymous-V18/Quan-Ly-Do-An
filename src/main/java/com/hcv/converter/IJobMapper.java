package com.hcv.converter;

import com.hcv.dto.JobDTO;
import com.hcv.dto.request.JobInput;
import com.hcv.entity.JobEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IJobMapper {
    JobDTO toDTO(JobEntity jobEntity);

    JobEntity toEntity(JobDTO jobDTO);

    JobEntity toEntity(JobInput jobInput);

    JobDTO toDTO(JobInput jobInput);
}
