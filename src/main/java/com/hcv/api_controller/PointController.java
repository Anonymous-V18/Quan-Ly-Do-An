package com.hcv.api_controller;

import com.hcv.dto.request.PointInsertInput;
import com.hcv.dto.request.PointUpdateInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.PointResponse;
import com.hcv.service.IPointService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/points")
public class PointController {

    IPointService pointService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<PointResponse> insert(@RequestBody PointInsertInput pointInsertInput) {
        PointResponse response = pointService.insert(pointInsertInput);
        return ApiResponse.<PointResponse>builder()
                .result(response)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<PointResponse> update(@PathVariable(value = "id") String id,
                                             @RequestBody PointUpdateInput pointUpdateInput) {
        PointResponse response = pointService.update(id, pointUpdateInput);
        return ApiResponse.<PointResponse>builder()
                .result(response)
                .build();
    }

//    @DeleteMapping("/delete")
//    @PreAuthorize("hasRole('TEACHER')")
//    public ApiResponse<String> delete(@RequestBody String[] ids) {
//        pointService.delete(ids);
//        return ApiResponse.<String>builder()
//                .message("Xóa thành công !")
//                .build();
//    }

}
