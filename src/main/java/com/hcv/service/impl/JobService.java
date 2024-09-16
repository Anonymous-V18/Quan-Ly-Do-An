package com.hcv.service.impl;

import com.hcv.constant.JobNameConst;
import com.hcv.converter.IJobMapper;
import com.hcv.dto.request.JobInput;
import com.hcv.dto.response.JobDTO;
import com.hcv.entity.Group;
import com.hcv.entity.Job;
import com.hcv.entity.Teacher;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IGroupRepository;
import com.hcv.repository.IJobRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.service.IJobService;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobService implements IJobService {

    IJobRepository jobRepository;
    IJobMapper jobMapper;
    ITeacherRepository teacherRepository;
    IUserService userService;
    IGroupRepository groupRepository;

    @Override
    public JobDTO insert(JobInput jobInput) {
        Job job = jobMapper.toEntity(jobInput);

        job.setIsCompleted(0);

        String teacherId = userService.getClaimsToken().get("sub").toString();
        Teacher sender = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
        job.setSendFrom(teacherId);

        String receiverId = jobInput.getSendTo();
        switch (jobInput.getName().trim()) {

            case JobNameConst.NAME_PROCESS_1,
                 JobNameConst.NAME_PROCESS_2 -> {
                Teacher teacher = teacherRepository.findById(receiverId)
                        .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
                job.setTeachers(List.of(teacher));
            }
            case JobNameConst.NAME_PROCESS_3 -> {
                Group group = groupRepository.findById(receiverId)
                        .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
                job.setTeachers(List.of(sender));
                job.setGroups(group);
            }
        }

        job.setIsCompleted(0);
        job = jobRepository.save(job);

        return jobMapper.toDTO(job);
    }

    @Override
    public void deleteJobOfTeacherCompleted(String[] ids) {
        for (String id : ids) {
            jobRepository.deleteById(id);
        }
    }

    @Override
    public JobDTO markCompleted(String id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_EXIST));
        job.setIsCompleted(1);
        job = jobRepository.save(job);
        return jobMapper.toDTO(job);
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> resultEntity = jobRepository.findAll();
        return resultEntity.stream().map(jobMapper::toDTO).toList();
    }


}
