package com.hcv.api_controller;

import com.hcv.dto.JobDTO;
import com.hcv.dto.request.JobForTeacherInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.service.IJobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobAPI {

    private final IJobService jobService;

    @PostMapping("/teacher-job/insert")
    public ResponseEntity<?> addJobForTeacher(@RequestBody @Valid JobForTeacherInput jobForTeacherInput) {
        JobDTO jobDTO = jobService.insert(jobForTeacherInput);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(jobDTO).build(), HttpStatus.CREATED);
    }

    @DeleteMapping("/teacher-job/delete")
    public ResponseEntity<?> deleteJobOfTeacherCompleted(@RequestBody Long[] ids) {
        jobService.deleteJobOfTeacherCompleted(ids);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).message("Successfully !").build(), HttpStatus.OK);
    }

    @GetMapping("/teacher-job/show-job-not-complete")
    public ResponseEntity<?> showJobNotComplete(@RequestBody String maSoGV) {
        List<JobDTO> results = jobService.showAllJobNotComplete(maSoGV);
        if (results.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No job not completed !").build(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(results).build(), HttpStatus.CREATED);
    }


}
