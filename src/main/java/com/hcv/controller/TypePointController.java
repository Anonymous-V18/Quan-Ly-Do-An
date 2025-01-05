package com.hcv.controller;

import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.TypePointResponse;
import com.hcv.service.ITypePointService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/type-points")
public class TypePointController {

    ITypePointService typePointService;

    @GetMapping("/showAll-no-params")
    public ApiResponse<List<TypePointResponse>> showAll() {
        List<TypePointResponse> response = typePointService.showAll();
        return ApiResponse.<List<TypePointResponse>>builder()
                .result(response)
                .build();
    }

}
