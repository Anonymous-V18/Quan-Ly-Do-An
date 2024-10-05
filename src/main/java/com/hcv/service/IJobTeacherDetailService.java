package com.hcv.service;

import com.hcv.entity.JobTeacherDetail;

import java.util.List;

public interface IJobTeacherDetailService {

    void updateQuantityCompleted(JobTeacherDetail jobTeacherDetail, int quantity, boolean isIncrease);

    JobTeacherDetail findJobTeacherDetailId(List<JobTeacherDetail> jobTeacherDetails);

}