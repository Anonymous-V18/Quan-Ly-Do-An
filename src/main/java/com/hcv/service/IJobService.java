package com.hcv.service;

import com.hcv.dto.request.JobInput;
import com.hcv.dto.response.JobDTO;

import java.util.List;

public interface IJobService {

    JobDTO insert(JobInput jobInput);

    void deleteJobOfTeacherCompleted(String[] ids);

    List<JobDTO> showAllJobNotComplete(String maSoGV);

    List<JobDTO> findAll();

}
