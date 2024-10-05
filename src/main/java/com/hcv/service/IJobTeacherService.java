package com.hcv.service;

import com.hcv.dto.request.JobTeacherInput;
import com.hcv.dto.request.JobTeacherUpdateInput;
import com.hcv.dto.response.JobTeacherResponse;
import com.hcv.dto.response.JobTeacherShortenedResponse;

import java.util.List;

public interface IJobTeacherService {

    JobTeacherResponse insert(JobTeacherInput jobInput);

    JobTeacherResponse update(String oldJobId, JobTeacherUpdateInput jobTeacherUpdateInput);

    JobTeacherResponse findById(String jobId);

    List<JobTeacherShortenedResponse> showAllJobDelivered();

    List<JobTeacherShortenedResponse> showMyJobShortened();
}
