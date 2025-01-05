package com.hcv.controller;

import com.hcv.dto.request.CouncilInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.CouncilResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.ICouncilService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/councils")
public class CouncilController {

    ICouncilService councilService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> insert(@RequestBody @Valid CouncilInput councilInput) {
        councilService.insert(councilInput);
        return ApiResponse.<String>builder()
                .message("Thêm thành công !")
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> update(@PathVariable(name = "id") String id,
                                      @RequestBody @Valid CouncilInput councilInput) {

        councilService.update(id, councilInput);
        return ApiResponse.<String>builder()
                .message("Cập nhật thành công !")
                .build();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> delete(@RequestBody String[] ids) {
        councilService.delete(ids);
        return ApiResponse.<String>builder()
                .message("Chưa viết API này !")
                .build();
    }

    @GetMapping("/showAll")
    public ApiResponse<ShowAllResponse<CouncilResponse>> showAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
            @RequestParam(value = "orderDirection", required = false, defaultValue = "ASC") String orderDirection
    ) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .currentPage(page)
                .limit(limit)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();
        ShowAllResponse<CouncilResponse> response = councilService.showAll(showAllRequest);
        return ApiResponse.<ShowAllResponse<CouncilResponse>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/getDetail")
    public ApiResponse<CouncilResponse> getDetail(@RequestParam(name = "councilId") String councilId) {
        CouncilResponse response = councilService.getDetail(councilId);
        return ApiResponse.<CouncilResponse>builder()
                .result(response)
                .build();
    }

}
