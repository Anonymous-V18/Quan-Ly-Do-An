package com.hcv.service.impl;

import com.hcv.converter.ITypePointMapper;
import com.hcv.dto.response.TypePointResponse;
import com.hcv.repository.ITypePointRepository;
import com.hcv.service.ITypePointService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypePointService implements ITypePointService {

    ITypePointRepository typePointRepository;
    ITypePointMapper typePointMapper;

    @Override
    public List<TypePointResponse> showAll() {
        return typePointRepository.findAll().stream().map(typePointMapper::toDTO).toList();
    }
    
}
