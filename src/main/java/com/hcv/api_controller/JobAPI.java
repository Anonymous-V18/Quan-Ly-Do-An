package com.hcv.api_controller;

import com.hcv.dto.request.JobInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.JobDTO;
import com.hcv.service.IJobService;
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
@RequestMapping("/jobs")
public class JobAPI {

    IJobService jobService;

    @PostMapping("/teacher-job/insert")
    @PreAuthorize("hasAnyRole('DEAN','HEAD_OF_DEPARTMENT')")
    public ApiResponse<JobDTO> insert(@RequestBody @Valid JobInput jobInput) {
        JobDTO jobDTO = jobService.insert(jobInput);
        return ApiResponse.<JobDTO>builder()
                .result(jobDTO)
                .build();
    }

    @PostMapping("/researchers-job/insert")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<JobDTO> insertJobForResearchers(@RequestBody @Valid JobInput jobInput) {
        JobDTO jobDTO = jobService.insert(jobInput);
        return ApiResponse.<JobDTO>builder()
                .result(jobDTO)
                .build();
    }

    @DeleteMapping("/teacher-job/delete")
    public ApiResponse<String> delete(@RequestBody String[] ids) {
        jobService.deleteJobOfTeacherCompleted(ids);
        return ApiResponse.<String>builder()
                .message("Xóa công việc thành công !")
                .build();
    }

    @GetMapping("/showAll-no-params")
    public ApiResponse<List<JobDTO>> showAll() {
        List<JobDTO> response = jobService.findAll();
        return ApiResponse.<List<JobDTO>>builder()
                .result(response)
                .build();
    }


    @GetMapping("/teacher-job/show-job-not-complete")
    public ApiResponse<List<JobDTO>> showJobNotComplete(@RequestParam(name = "maSo") String maSoGV) {
        List<JobDTO> results = jobService.showAllJobNotComplete(maSoGV);
        return ApiResponse.<List<JobDTO>>builder()
                .result(results)
                .build();
    }

}
