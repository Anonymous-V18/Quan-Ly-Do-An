package com.hcv.service;

import com.hcv.dto.JobDTO;
import com.hcv.dto.input.JobForTeacherInput;

import java.util.List;

public interface IJobService {

    JobDTO insert(JobForTeacherInput jobForTeacherInput);

    void deleteJobOfTeacherCompleted(Long[] ids);

    List<JobDTO> showAllJobNotComplete(String maSoGV);

}
