package com.hcv.dto.request;

import com.hcv.converter.IRoleMapper;
import com.hcv.dto.response.RoleDTO;
import com.hcv.repository.IRoleRepository;
import com.hcv.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService implements IRoleService {

    IRoleRepository roleRepository;
    IRoleMapper roleMapper;

    @Override
    public List<RoleDTO> showAll() {
        return roleRepository.findAll().stream().map(roleMapper::toDTO).toList();
    }
    
}
