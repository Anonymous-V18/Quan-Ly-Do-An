package com.hcv.converter;

import com.hcv.dto.response.GroupDTO;
import com.hcv.dto.response.GroupResponse;
import com.hcv.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IGroupMapper {

    @Mapping(target = "jobs", ignore = true)
    Group toEntity(GroupDTO groupDTO);

    GroupDTO toDTO(Group group);

    GroupResponse toShowDTO(Group group);

}
