package com.hcv.api_controller;

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
public class DepartmentAPI {

    IDepartmentService departmentService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<DepartmentDTO> insert(@RequestBody @Valid DepartmentDTO departmentDTO) {
        DepartmentDTO newDepartmentDTO = departmentService.insert(departmentDTO);
        return ApiResponse.<DepartmentDTO>builder()
                .result(newDepartmentDTO)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<DepartmentDTO> update(@PathVariable(value = "id") String id,
                                             @RequestBody @Valid DepartmentDTO newDepartmentDTO) {
        DepartmentDTO updatedDTO = departmentService.update(id, newDepartmentDTO);
        return ApiResponse.<DepartmentDTO>builder()
                .result(updatedDTO)
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
    public ApiResponse<ShowAllResponse<DepartmentDTO>> showAll(@RequestParam(name = "page") Integer page,
                                                               @RequestParam(name = "limit") Integer limit,
                                                               @RequestParam(name = "orderBy") String orderBy,
                                                               @RequestParam(name = "orderDirection") String orderDirection) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .page(page)
                .limit(limit)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();
        ShowAllResponse<DepartmentDTO> resultList = departmentService.showAll(showAllRequest);
        return ApiResponse.<ShowAllResponse<DepartmentDTO>>builder()
                .result(resultList)
                .build();
    }

    @GetMapping("/showAll-no-params")
    public ApiResponse<List<DepartmentDTO>> showAll() {
        List<DepartmentDTO> response = departmentService.findAll();
        return ApiResponse.<List<DepartmentDTO>>builder()
                .result(response)
                .build();
    }


    @GetMapping("/showOne")
    public ApiResponse<DepartmentDTO> showOne(@RequestParam(name = "name") String name) {
        DepartmentDTO dto = departmentService.findOneByName(name);
        return ApiResponse.<DepartmentDTO>builder()
                .result(dto)
                .build();
    }


}
