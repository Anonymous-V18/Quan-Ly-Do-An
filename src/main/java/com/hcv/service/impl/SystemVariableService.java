package com.hcv.service.impl;

import com.hcv.converter.ISystemVariableMapper;
import com.hcv.dto.request.SystemVariableInput;
import com.hcv.dto.response.SystemVariableResponse;
import com.hcv.entity.SystemVariable;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.ISystemVariableRepository;
import com.hcv.service.ISystemVariableService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SystemVariableService implements ISystemVariableService {

    ISystemVariableRepository systemVariableRepository;
    ISystemVariableMapper systemVariableMapper;

    @Override
    public SystemVariableResponse update(String systemVariableId, SystemVariableInput systemVariableInput) {
        SystemVariable systemVariable = systemVariableRepository.findById(systemVariableId)
                .orElseThrow(() -> new AppException(ErrorCode.SYSTEM_VARIABLE_INVALID));
        systemVariable = systemVariableMapper.toEntity(systemVariable, systemVariableInput);
        systemVariable = systemVariableRepository.save(systemVariable);
        return systemVariableMapper.toDTO(systemVariable);
    }

    @Override
    public List<SystemVariableResponse> showAll() {
        return systemVariableRepository.findAll().stream().map(systemVariableMapper::toDTO).toList();
    }

}
