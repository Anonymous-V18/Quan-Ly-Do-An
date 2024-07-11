package com.hcv.api_controller;

import com.hcv.dto.SubjectDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.SubjectInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.ISubjectService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/subjects")
public class SubjectAPI {

    ISubjectService subjectService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<SubjectDTO> insert(@RequestBody @Valid SubjectInput subjectInput) {
        SubjectDTO newSubjectDTO = subjectService.insert(subjectInput);
        return ApiResponse.<SubjectDTO>builder()
                .result(newSubjectDTO)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<SubjectDTO> update(@PathVariable(name = "id") String id,
                                          @RequestBody @Valid SubjectInput subjectInput) {
        SubjectDTO oldSubjectDTO = subjectService.findOneById(id);
        if (oldSubjectDTO == null) {
            SubjectDTO newSubjectDTO = subjectService.insert(subjectInput);
            return ApiResponse.<SubjectDTO>builder()
                    .result(newSubjectDTO)
                    .build();
        }
        SubjectDTO updatedDTO = subjectService.update(oldSubjectDTO, subjectInput);
        return ApiResponse.<SubjectDTO>builder()
                .result(updatedDTO)
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
    public ApiResponse<ShowAllResponse<SubjectDTO>> showAll(@RequestParam(name = "page") Integer page,
                                                            @RequestParam(name = "limit") Integer limit,
                                                            @RequestParam(name = "orderBy") String orderBy,
                                                            @RequestParam(name = "orderDirection") String orderDirection) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .page(page)
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
    public ApiResponse<List<SubjectDTO>> showAll() {
        List<SubjectDTO> response = subjectService.findAll();
        return ApiResponse.<List<SubjectDTO>>builder()
                .result(response)
                .build();
    }


    @GetMapping("/showOne")
    public ApiResponse<SubjectDTO> showOne(@RequestParam(name = "name") String name) {
        SubjectDTO subjectDTO = subjectService.findOneByName(name);
        return ApiResponse.<SubjectDTO>builder()
                .result(subjectDTO)
                .build();
    }

}
