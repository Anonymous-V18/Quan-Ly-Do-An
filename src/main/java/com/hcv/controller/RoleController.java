package com.hcv.controller;

import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.RoleDTO;
import com.hcv.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/roles")
public class RoleController {

    IRoleService roleService;

    @GetMapping("/showAll-no-params")
    @PreAuthorize("hasRole('DEAN') or hasRole('ADMIN') or hasRole('CATECHISM')")
    public ApiResponse<List<RoleDTO>> showAll() {
        List<RoleDTO> response = roleService.showAll();
        return ApiResponse.<List<RoleDTO>>builder()
                .result(response)
                .build();
    }
    
}
