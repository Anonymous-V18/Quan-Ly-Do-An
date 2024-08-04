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
public class PointAPI {

    IPointService pointService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<PointResponse> insert(@RequestBody PointInsertInput pointInsertInput) {
        PointResponse pointDTO = pointService.insert(pointInsertInput);
        return ApiResponse.<PointResponse>builder()
                .result(pointDTO)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<PointResponse> update(@PathVariable(value = "id") String id,
                                             @RequestBody PointUpdateInput pointUpdateInput) {
        PointResponse updateDTO = pointService.update(id, pointUpdateInput);
        return ApiResponse.<PointResponse>builder()
                .result(updateDTO)
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
