package com.hcv.controller;

import com.hcv.dto.request.SystemVariableInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.SystemVariableResponse;
import com.hcv.service.ISystemVariableService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/system-variables")
public class SystemVariableController {

    ISystemVariableService systemVariableService;

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('ADMIN')")
    public ApiResponse<SystemVariableResponse> update(@PathVariable(value = "id") String systemVariableId,
                                                      @RequestBody SystemVariableInput systemVariableInput) {
        SystemVariableResponse response = systemVariableService.update(systemVariableId, systemVariableInput);
        return ApiResponse.<SystemVariableResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-no-params")
    public ApiResponse<List<SystemVariableResponse>> showAll() {
        List<SystemVariableResponse> response = systemVariableService.showAll();
        return ApiResponse.<List<SystemVariableResponse>>builder()
                .result(response)
                .build();
    }
}
