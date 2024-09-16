package com.hcv.api_controller;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.SubjectInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.SubjectDTO;
import com.hcv.dto.response.SubjectResponse;
import com.hcv.service.ISubjectService;
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
@RequestMapping("/subjects")
public class SubjectController {

    ISubjectService subjectService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<SubjectDTO> insert(@RequestBody @Valid SubjectInput subjectInput) {
        SubjectDTO response = subjectService.insert(subjectInput);
        return ApiResponse.<SubjectDTO>builder()
                .result(response)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<SubjectDTO> update(@PathVariable(name = "id") String id,
                                          @RequestBody @Valid SubjectInput subjectInput) {

        SubjectDTO response = subjectService.update(id, subjectInput);
        return ApiResponse.<SubjectDTO>builder()
                .result(response)
                .build();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> delete(@RequestBody String[] ids) {
        subjectService.delete(ids);
        return ApiResponse.<String>builder()
                .message("Xóa bộ môn thành công !")
                .build();
    }

    @GetMapping("/showAll")
    public ApiResponse<ShowAllResponse<SubjectDTO>> showAll(
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
        ShowAllResponse<SubjectDTO> response = subjectService.showAll(showAllRequest);
        return ApiResponse.<ShowAllResponse<SubjectDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-no-params")
    public ApiResponse<List<SubjectDTO>> getAll() {
        List<SubjectDTO> response = subjectService.findAll();
        return ApiResponse.<List<SubjectDTO>>builder()
                .result(response)
                .build();
    }


    @GetMapping("/showOne")
    public ApiResponse<SubjectDTO> showOne(@RequestParam(name = "name") String name) {
        SubjectDTO response = subjectService.findOneByName(name);
        return ApiResponse.<SubjectDTO>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-by-department")
    public ApiResponse<List<SubjectResponse>> showAllByDepartment(@RequestParam(name = "departmentId") String departmentId) {
        List<SubjectResponse> response = subjectService.showAllByDepartment(departmentId);
        return ApiResponse.<List<SubjectResponse>>builder()
                .result(response)
                .build();
    }
}
