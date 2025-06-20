package com.hcv.controller;

import com.hcv.dto.request.PointInsertListInput;
import com.hcv.dto.request.PointUpdateListInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.PointResponse;
import com.hcv.service.IPointService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/points")
public class PointController {

    IPointService pointService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<List<PointResponse>> insert(@RequestBody @Valid PointInsertListInput pointInsertListInput) {
        List<PointResponse> response = pointService.insertList(pointInsertListInput);
        return ApiResponse.<List<PointResponse>>builder()
                .result(response)
                .build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<List<PointResponse>> update(@RequestBody @Valid PointUpdateListInput pointUpdateListInput) {
        List<PointResponse> response = pointService.updateList(pointUpdateListInput);
        return ApiResponse.<List<PointResponse>>builder()
                .result(response)
                .build();
    }


}
