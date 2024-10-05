package com.hcv.converter;

import com.hcv.dto.request.JobGroupInput;
import com.hcv.dto.request.JobTeacherInput;
import com.hcv.dto.request.JobTeacherUpdateInput;
import com.hcv.dto.response.JobGroupResponse;
import com.hcv.dto.response.JobGroupShortenedResponse;
import com.hcv.dto.response.JobTeacherResponse;
import com.hcv.dto.response.JobTeacherShortenedResponse;
import com.hcv.entity.JobGroup;
import com.hcv.entity.JobTeacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface IJobMapper {

    JobTeacherShortenedResponse toShortenedDTO(JobTeacher jobTeacher);

    JobTeacherResponse toDTO(JobTeacher jobTeacher);

    JobGroupShortenedResponse toShortenedDTO(JobGroup jobGroup);

    JobGroupResponse toDTO(JobGroup jobGroup);

    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "isCompleted", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    JobGroup toEntity(JobGroupInput jobGroupInput);

    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    JobGroup toEntity(@MappingTarget JobGroup oldJobGroup, JobGroupInput jobGroupInput);

    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    JobTeacher toEntity(JobTeacherInput jobTeacherInput);

    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    JobTeacher toEntity(@MappingTarget JobTeacher oldJobTeacher, JobTeacherUpdateInput jobTeacherUpdateInput);

}
