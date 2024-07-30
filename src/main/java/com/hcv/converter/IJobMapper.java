package com.hcv.converter;

import com.hcv.dto.JobDTO;
import com.hcv.dto.request.job.JobInput;
import com.hcv.entity.JobEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IJobMapper {

    JobDTO toDTO(JobEntity jobEntity);

    JobEntity toEntity(JobInput jobInput);

}
