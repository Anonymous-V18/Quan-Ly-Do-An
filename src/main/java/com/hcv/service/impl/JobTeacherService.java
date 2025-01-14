package com.hcv.service.impl;

import com.hcv.constant.JobTypeConst;
import com.hcv.converter.IJobMapper;
import com.hcv.dto.request.JobTeacherInput;
import com.hcv.dto.request.JobTeacherUpdateInput;
import com.hcv.dto.response.JobTeacherResponse;
import com.hcv.dto.response.JobTeacherShortenedResponse;
import com.hcv.entity.JobTeacher;
import com.hcv.entity.JobTeacherDetail;
import com.hcv.entity.Teacher;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IJobTeacherDetailRepository;
import com.hcv.repository.IJobTeacherRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.service.IJobTeacherDetailService;
import com.hcv.service.IJobTeacherService;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobTeacherService implements IJobTeacherService {

    IJobTeacherRepository jobTeacherRepository;
    IJobTeacherDetailRepository jobTeacherDetailRepository;
    IJobTeacherDetailService jobTeacherDetailService;
    ITeacherRepository teacherRepository;
    IJobMapper jobTeacherMapper;
    IUserService userService;


    @Override
    @Transactional
    public JobTeacherResponse insert(JobTeacherInput jobInput) {
        JobTeacher jobTeacher = jobTeacherMapper.toEntity(jobInput);

        String currentUserId = userService.getClaimsToken().get("sub").toString();
        Teacher teacher = teacherRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
        jobTeacher.setSenderCode(teacher.getCode());
        jobTeacher.setSenderName(teacher.getName());
        List<String> listRole = Arrays.stream(userService.getClaimsToken().get("scope").toString().split(" ")).toList();
        if (listRole.contains("DEAN")) {
            jobTeacher.setType(JobTypeConst.PROCESS_TYPE_1);
        } else {
            jobTeacher.setType(JobTypeConst.PROCESS_TYPE_2);
        }

        List<String> teacherIds = jobInput.getTeacherIds();
        List<Teacher> teachers = teacherRepository.findAllById(teacherIds);
        if (teachers.size() != teacherIds.size()) {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }

        JobTeacherDetail jobTeacherDetail;
        for (Teacher item : teachers) {
            jobTeacherDetail = new JobTeacherDetail();
            jobTeacherDetail.setIsCompleted(0);
            jobTeacherDetail.setQuantityCompleted(0);
            jobTeacherDetail.setTeacher(item);
            jobTeacherDetail.setJobTeacher(jobTeacher);
            jobTeacherDetail = jobTeacherDetailRepository.save(jobTeacherDetail);
            jobTeacher.getJobTeacherDetails().add(jobTeacherDetail);
        }

        jobTeacher = jobTeacherRepository.save(jobTeacher);

        if (!listRole.contains("DEAN")) {
            JobTeacherDetail jobTeacherDetailRoot = teacher.getJobTeacherDetails()
                    .stream()
                    .filter(jobDetail -> jobDetail.getIsCompleted() != 1
                            && jobDetail.getJobTeacher().getDue().isAfter(Instant.now())
                            && jobDetail.getJobTeacher().getType().equals(JobTypeConst.PROCESS_TYPE_1)
                    )
                    .findFirst()
                    .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
            int quantityCompleted = jobTeacher.getQuantityRequirement() * jobTeacher.getJobTeacherDetails().size();
            jobTeacherDetailService.updateQuantityCompleted(jobTeacherDetailRoot, quantityCompleted, true);
        }
        return jobTeacherMapper.toDTO(jobTeacher);
    }

    @Override
    public JobTeacherResponse update(String oldJobId, JobTeacherUpdateInput jobTeacherUpdateInput) {
        JobTeacher jobTeacher = jobTeacherRepository.findById(oldJobId)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_EXIST));

        jobTeacher = jobTeacherMapper.toEntity(jobTeacher, jobTeacherUpdateInput);

        jobTeacher = jobTeacherRepository.save(jobTeacher);
        return jobTeacherMapper.toDTO(jobTeacher);
    }

    @Override
    public JobTeacherResponse findById(String jobId) {
        JobTeacher jobTeacher = jobTeacherRepository.findById(jobId)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_EXIST));
        return jobTeacherMapper.toDTO(jobTeacher);
    }

    @Override
    public List<JobTeacherShortenedResponse> showAllJobDelivered() {
        String teacherCode = userService.getClaimsToken().get("username").toString();
        return jobTeacherRepository.findBySenderCode(teacherCode).stream().map(jobTeacherMapper::toShortenedDTO).toList();
    }

    //TODO: Write code to auto decrease quantity completed for my job when I delete a job
    @Override
    public List<JobTeacherShortenedResponse> showMyJobShortened() {
        String currentUserId = userService.getClaimsToken().get("sub").toString();
        return jobTeacherDetailRepository.findByTeacher_Id(currentUserId).stream()
                .map(JobTeacherDetail::getJobTeacher)
                .sorted(Comparator.comparing(JobTeacher::getCreatedDate).reversed())
                .map(jobTeacher -> {
                    JobTeacherShortenedResponse response = jobTeacherMapper.toShortenedDTO(jobTeacher);
                    jobTeacher.getJobTeacherDetails().forEach(jobTeacherDetail -> {
                        if (jobTeacherDetail.getTeacher().getId().equals(currentUserId)) {
                            response.setIsCompleted(jobTeacherDetail.getIsCompleted());
                        }
                    });
                    return response;
                })
                .toList();
    }
}
