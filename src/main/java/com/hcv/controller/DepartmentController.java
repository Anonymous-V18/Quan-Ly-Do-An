package com.hcv.controller;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.DepartmentDTO;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.IDepartmentService;
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
@RequestMapping("/departments")
public class DepartmentController {

    IDepartmentService departmentService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<DepartmentDTO> insert(@RequestBody @Valid DepartmentDTO departmentDTO) {
        DepartmentDTO response = departmentService.insert(departmentDTO);
        return ApiResponse.<DepartmentDTO>builder()
                .result(response)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<DepartmentDTO> update(@PathVariable(value = "id") String id,
                                             @RequestBody @Valid DepartmentDTO newDepartmentDTO) {
        DepartmentDTO response = departmentService.update(id, newDepartmentDTO);
        return ApiResponse.<DepartmentDTO>builder()
                .result(response)
                .build();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> delete(@RequestBody String[] ids) {
        departmentService.delete(ids);
        return ApiResponse.<String>builder()
                .message("Xóa thành công !")
                .build();
    }

    @GetMapping("/showAll")
    public ApiResponse<ShowAllResponse<DepartmentDTO>> showAll(
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
        ShowAllResponse<DepartmentDTO> response = departmentService.showAll(showAllRequest);
        return ApiResponse.<ShowAllResponse<DepartmentDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-no-params")
    public ApiResponse<List<DepartmentDTO>> getAll() {
        List<DepartmentDTO> response = departmentService.findAll();
        return ApiResponse.<List<DepartmentDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showOne")
    public ApiResponse<DepartmentDTO> showOne(@RequestParam(name = "name") String name) {
        DepartmentDTO response = departmentService.findOneByName(name);
        return ApiResponse.<DepartmentDTO>builder()
                .result(response)
                .build();
    }

}
