package com.hcv.service;

import com.hcv.api_controller.input.JobForTeacherInput;
import com.hcv.dto.JobDTO;

import java.util.List;

public interface IJobService {

    JobDTO insert(JobForTeacherInput jobForTeacherInput);

    void deleteJobOfTeacherCompleted(Long[] ids);

    List<JobDTO> showAllJobNotComplete(String maSoGV);

}
