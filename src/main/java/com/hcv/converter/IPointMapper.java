package com.hcv.converter;

import com.hcv.dto.PointDTO;
import com.hcv.dto.request.point.PointInsertInput;
import com.hcv.dto.request.point.PointUpdateInput;
import com.hcv.dto.response.PointResponse;
import com.hcv.entity.PointEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface IPointMapper {

    PointResponse toDTO(PointEntity pointEntity);

    PointDTO toDTO(@MappingTarget PointDTO oldPointDTO, PointUpdateInput newPointDTO);

    PointEntity toEntity(PointInsertInput pointInsertInput);

    PointEntity toEntity(PointDTO pointDTO);

}
