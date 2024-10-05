package com.hcv.converter;

import com.hcv.dto.request.PointInsertInput;
import com.hcv.dto.response.PointResponse;
import com.hcv.entity.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IPointMapper {

    PointResponse toDTO(Point point);

    @Mapping(target = "teacherId", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "id", ignore = true)
    Point toEntity(PointInsertInput pointInsertInput);

}
