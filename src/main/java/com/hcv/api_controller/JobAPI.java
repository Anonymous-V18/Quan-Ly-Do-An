package com.hcv.api_controller;

import com.hcv.dto.JobDTO;
import com.hcv.dto.request.JobForTeacherInput;
import com.hcv.service.IJobService;
import com.hcv.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/jobs")
public class JobAPI {

    @Autowired
    private IJobService jobService;
    @Autowired
    private ITeacherService teacherService;

    @PostMapping("/teacher-job/insert")
    public ResponseEntity<?> addJobForTeacher(@RequestBody JobForTeacherInput jobForTeacherInput) {
        jobService.insert(jobForTeacherInput);
        return new ResponseEntity<>("Successfully !", HttpStatus.CREATED);
    }

    @DeleteMapping("/teacher-job/delete")
    public ResponseEntity<?> deleteJobOfTeacherCompleted(@RequestBody Long[] ids) {
        jobService.deleteJobOfTeacherCompleted(ids);
        return new ResponseEntity<>("Successfully !", HttpStatus.OK);
    }

    @GetMapping("/teacher-job/show-job-not-complete")
    public ResponseEntity<?> showJobNotComplete(@RequestBody String maSoGV) {
        List<JobDTO> results = jobService.showAllJobNotComplete(maSoGV);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


}
