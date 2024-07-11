package com.hcv.api_controller;

import com.hcv.dto.JobDTO;
import com.hcv.dto.UserDTO;
import com.hcv.dto.request.JobForTeacherInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.service.IJobService;
import com.hcv.service.IUserService;
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
@RequestMapping("/jobs")
public class JobAPI {

    IJobService jobService;
    IUserService userService;

    @PostMapping("/teacher-job/insert")
    @PreAuthorize("hasAnyRole('DEAN','HEAD_OF_DEPARTMENT')")
    public ApiResponse<JobDTO> addJobForTeacher(@RequestBody @Valid JobForTeacherInput jobForTeacherInput) {
        UserDTO userDTO = userService.findOneByUsername(jobForTeacherInput.getSendFrom());
        if (userDTO == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        JobDTO jobDTO = jobService.insert(jobForTeacherInput);
        return ApiResponse.<JobDTO>builder()
                .result(jobDTO)
                .build();
    }

    @DeleteMapping("/teacher-job/delete")
    public ApiResponse<String> deleteJobOfTeacherCompleted(@RequestBody String[] ids) {
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
