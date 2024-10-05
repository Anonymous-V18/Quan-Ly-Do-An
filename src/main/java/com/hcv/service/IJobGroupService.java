package com.hcv.service;

import com.hcv.dto.request.JobGroupInput;
import com.hcv.dto.response.JobGroupResponse;

public interface IJobGroupService {

    JobGroupResponse insert(JobGroupInput jobInput);

    JobGroupResponse update(String oldJobId, JobGroupInput jobInput);

    void markCompleted(String groupId);

    void delete(String[] jobId);

    JobGroupResponse findById(String groupId);
}
