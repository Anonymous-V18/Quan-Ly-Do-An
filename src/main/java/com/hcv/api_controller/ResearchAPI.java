package com.hcv.api_controller;

import com.hcv.dto.ResearchDTO;
import com.hcv.dto.request.CancelRegistrationResearchInput;
import com.hcv.dto.request.RegisterResearchInput;
import com.hcv.dto.request.ResearchInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.IResearchService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/researches")
public class ResearchAPI {

    IResearchService researchService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<ResearchDTO> insert(@RequestBody @Valid ResearchInput researchInput) {
        ResearchDTO newResearch = researchService.insert(researchInput);
        return ApiResponse.<ResearchDTO>builder()
                .result(newResearch)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<ResearchDTO> update(@PathVariable(value = "id") String id,
                                           @RequestBody ResearchInput researchInput) {
        ResearchDTO oldResearchDTO = researchService.findOneById(id);
        ResearchDTO updateDTO;
        if (oldResearchDTO == null) {
            updateDTO = researchService.insert(researchInput);
        } else {
            updateDTO = researchService.update(oldResearchDTO, researchInput);
        }
        return ApiResponse.<ResearchDTO>builder()
                .result(updateDTO)
                .build();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<String> delete(@RequestBody String[] ids) {
        researchService.delete(ids);
        return ApiResponse.<String>builder()
                .message("Xóa đề tài thành công !")
                .build();
    }

    @GetMapping("/showAll")
    public ApiResponse<ShowAllResponse<ResearchResponse>> showAll(@RequestParam(name = "page") Integer page,
                                                                  @RequestParam(name = "limit") Integer limit,
                                                                  @RequestParam(name = "orderBy") String orderBy,
                                                                  @RequestParam(name = "orderDirection") String orderDirection) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .page(page)
                .limit(limit)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();
        ShowAllResponse<ResearchResponse> response = researchService.showAll(showAllRequest);
        return ApiResponse.<ShowAllResponse<ResearchResponse>>builder()
                .result(response)
                .build();
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<String> register(@RequestBody RegisterResearchInput registerResearchInput) {
        researchService.registerResearch(registerResearchInput);
        return ApiResponse.<String>builder()
                .message("Đăng ký đề tài thành công !")
                .build();
    }

    @DeleteMapping("/cancel-registration")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<String> cancelRegistration(@RequestBody CancelRegistrationResearchInput cancelRegistrationResearchInput) {
        researchService.cancelRegistrationResearch(cancelRegistrationResearchInput);
        return ApiResponse.<String>builder()
                .message("Hủy dăng ký đề tài thành công !")
                .build();
    }
    
}
