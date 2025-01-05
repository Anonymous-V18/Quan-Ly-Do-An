package com.hcv.controller;

import com.hcv.dto.request.JobTeacherInput;
import com.hcv.dto.request.JobTeacherUpdateInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.JobTeacherResponse;
import com.hcv.dto.response.JobTeacherShortenedResponse;
import com.hcv.service.IJobTeacherService;
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
@RequestMapping("/job-teacher")
public class JobTeacherController {

    IJobTeacherService jobTeacherService;

    @PostMapping("/insert")
    @PreAuthorize("hasAnyRole('HEAD_OF_DEPARTMENT','DEAN')")
    public ApiResponse<JobTeacherResponse> insert(@Valid @RequestBody JobTeacherInput jobInput) {
        JobTeacherResponse jobTeacherResponse = jobTeacherService.insert(jobInput);
        return ApiResponse.<JobTeacherResponse>builder()
                .result(jobTeacherResponse)
                .build();
    }

    @PutMapping("/update/{jobId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'HEAD_OF_DEPARTMENT','DEAN')")
    public ApiResponse<JobTeacherResponse> update(@PathVariable(value = "jobId") String jobId,
                                                  @RequestBody @Valid JobTeacherUpdateInput jobTeacherUpdateInput) {
        JobTeacherResponse jobTeacherResponse = jobTeacherService.update(jobId, jobTeacherUpdateInput);
        return ApiResponse.<JobTeacherResponse>builder()
                .result(jobTeacherResponse)
                .build();
    }

    @GetMapping("/showMyJob-delivered")
    public ApiResponse<List<JobTeacherShortenedResponse>> showAllJobDelivered() {
        List<JobTeacherShortenedResponse> response = jobTeacherService.showAllJobDelivered();
        return ApiResponse.<List<JobTeacherShortenedResponse>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showMyJob-no-params")
    public ApiResponse<List<JobTeacherShortenedResponse>> showMyJobShortened() {
        List<JobTeacherShortenedResponse> response = jobTeacherService.showMyJobShortened();
        return ApiResponse.<List<JobTeacherShortenedResponse>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showJob-detail")
    public ApiResponse<JobTeacherResponse> findById(@RequestParam(value = "jobId", required = false, defaultValue = "1") String jobId) {
        JobTeacherResponse response = jobTeacherService.findById(jobId);
        return ApiResponse.<JobTeacherResponse>builder()
                .result(response)
                .build();
    }

}
