package com.hcv.service;

import com.hcv.dto.JobDTO;
import com.hcv.dto.request.JobInput;

import java.util.List;

public interface IJobService {

    JobDTO insert(JobInput jobInput);

    void deleteJobOfTeacherCompleted(String[] ids);

    List<JobDTO> showAllJobNotComplete(String maSoGV);

    List<JobDTO> findAll();


}
