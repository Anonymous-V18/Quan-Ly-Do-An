package com.hcv.converter;

import com.hcv.dto.request.SystemVariableInput;
import com.hcv.dto.response.SystemVariableResponse;
import com.hcv.entity.SystemVariable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface ISystemVariableMapper {

    SystemVariableResponse toDTO(SystemVariable systemVariable);

    SystemVariable toEntity(SystemVariableInput systemVariableInput);

    SystemVariable toEntity(@MappingTarget SystemVariable oldSystemVariable, SystemVariableInput systemVariableInput);

}
