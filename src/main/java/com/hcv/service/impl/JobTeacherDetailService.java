package com.hcv.service.impl;

import com.hcv.constant.JobTypeConst;
import com.hcv.entity.JobTeacherDetail;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IJobTeacherDetailRepository;
import com.hcv.service.IJobTeacherDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobTeacherDetailService implements IJobTeacherDetailService {

    IJobTeacherDetailRepository jobTeacherDetailRepository;

    @Override
    public void updateQuantityCompleted(JobTeacherDetail jobTeacherDetail, int quantityIncrement, boolean isIncrease) {
        Integer quantityCompletedOld = jobTeacherDetail.getQuantityCompleted();
        Integer quantityCompletedNew = isIncrease
                ? quantityCompletedOld + quantityIncrement
                : quantityCompletedOld - quantityIncrement;
        if (quantityCompletedNew < 0) {
            quantityCompletedNew = 0;
        }
        jobTeacherDetail.setQuantityCompleted(quantityCompletedNew);
        if (quantityCompletedNew >= jobTeacherDetail.getJobTeacher().getQuantityRequirement()) {
            jobTeacherDetail.setIsCompleted(1);
        } else {
            jobTeacherDetail.setIsCompleted(0);
        }

        jobTeacherDetailRepository.save(jobTeacherDetail);
    }

    @Override
    public JobTeacherDetail findJobTeacherDetailId(List<JobTeacherDetail> jobTeacherDetails) {
        jobTeacherDetails = jobTeacherDetails.stream()
                .filter(jobTeacherDetail -> jobTeacherDetail.getJobTeacher().getDue().isAfter(Instant.now())
                        && jobTeacherDetail.getJobTeacher().getType().equals(JobTypeConst.PROCESS_TYPE_2)
                )
                .toList();
        if (jobTeacherDetails.isEmpty()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return jobTeacherDetails.getFirst();
    }

}
