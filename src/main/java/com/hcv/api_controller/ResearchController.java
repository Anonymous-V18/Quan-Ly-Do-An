package com.hcv.api_controller;

import com.hcv.dto.request.*;
import com.hcv.dto.response.*;
import com.hcv.service.IResearchService;
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
@RequestMapping("/researches")
public class ResearchController {

    IResearchService researchService;

    @PostMapping("/insert-from-file")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<List<ResearchDTO>> insert(@RequestBody @Valid ResearchInsertFromFileInput researchInsertFromFileInput) {
        List<ResearchDTO> response = researchService.insertFromFile(researchInsertFromFileInput);
        return ApiResponse.<List<ResearchDTO>>builder()
                .result(response)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<ResearchDTO> update(@PathVariable(value = "id") String id,
                                           @RequestBody ResearchUpdateInput researchUpdateInput) {
        ResearchDTO response = researchService.update(id, researchUpdateInput);
        return ApiResponse.<ResearchDTO>builder()
                .result(response)
                .build();
    }

    @PutMapping("/mark-approved/{id}")
    @PreAuthorize("hasRole('HEAD_OF_DEPARTMENT')")
    public ApiResponse<ResearchDTO> markApproved(@PathVariable(value = "id") String id) {
        ResearchDTO response = researchService.markApproved(id);
        return ApiResponse.<ResearchDTO>builder()
                .result(response)
                .build();
    }

    @PutMapping("/cancel-approval/{id}")
    @PreAuthorize("hasRole('HEAD_OF_DEPARTMENT')")
    public ApiResponse<ResearchDTO> cancelApproval(@PathVariable(value = "id") String id) {
        ResearchDTO response = researchService.cancelApproval(id);
        return ApiResponse.<ResearchDTO>builder()
                .result(response)
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

    @GetMapping("/showAll-my-research")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<ShowAllResponse<ResearchResponse>> showAllMyResearch
            (
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

        ShowAllResponse<ResearchResponse> response = researchService.showAllMyResearch(showAllRequest);
        return ApiResponse.<ShowAllResponse<ResearchResponse>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-to-feedback")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<ShowAllResponse<ResearchResponse>> showAllToFeedback
            (
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

        ShowAllResponse<ResearchResponse> response = researchService.showAllToFeedback(showAllRequest);
        return ApiResponse.<ShowAllResponse<ResearchResponse>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-to-approval-processing")
    @PreAuthorize("hasRole('HEAD_OF_DEPARTMENT')")
    public ApiResponse<ShowAllResponse<ResearchShowToRegistrationResponse>> showAllToApprovalProcessing
            (
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

        ShowAllResponse<ResearchShowToRegistrationResponse> response = researchService.showAllToApprovalProcessing(showAllRequest);
        return ApiResponse.<ShowAllResponse<ResearchShowToRegistrationResponse>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-to-registration")
    public ApiResponse<ShowAllResponse<ResearchShowToRegistrationResponse>> showAllToRegistration
            (
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

        ShowAllResponse<ResearchShowToRegistrationResponse> response = researchService.showAllToRegistration(showAllRequest);
        return ApiResponse.<ShowAllResponse<ResearchShowToRegistrationResponse>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showOne")
    public ApiResponse<ResearchResponse> showOne(@RequestParam("researchId") String id) {
        ResearchResponse response = researchService.showDetail(id);
        return ApiResponse.<ResearchResponse>builder()
                .result(response)
                .build();
    }


    @PostMapping("/register")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<String> register(@RequestBody ResearchRegistrationInput researchRegistrationInput) {
        researchService.registerResearch(researchRegistrationInput);
        return ApiResponse.<String>builder()
                .message("Đăng ký đề tài thành công !")
                .build();
    }

    @DeleteMapping("/cancel-registration")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<String> cancelRegistration(@RequestBody ResearchCancelRegistrationInput researchCancelRegistrationInput) {
        researchService.cancelRegistrationResearch(researchCancelRegistrationInput);
        return ApiResponse.<String>builder()
                .message("Hủy dăng ký đề tài thành công !")
                .build();
    }

}
