package com.hcv.service.impl;

import com.hcv.constant.JobNameConst;
import com.hcv.converter.IJobMapper;
import com.hcv.dto.request.JobInput;
import com.hcv.dto.response.JobDTO;
import com.hcv.entity.GroupEntity;
import com.hcv.entity.JobEntity;
import com.hcv.entity.TeacherEntity;
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
        JobEntity jobEntity = jobMapper.toEntity(jobInput);

        jobEntity.setIsCompleted(0);

        String teacherId = userService.getClaimsToken().get("sub").toString();
        TeacherEntity sender = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
        jobEntity.setSendFrom(teacherId);

        String receiverId = jobInput.getSendTo();
        switch (jobInput.getName().trim()) {

            case JobNameConst.NAME_PROCESS_1,
                 JobNameConst.NAME_PROCESS_2 -> {
                TeacherEntity teacherEntity = teacherRepository.findById(receiverId)
                        .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
                jobEntity.setTeachers(List.of(teacherEntity, sender));
            }
            case JobNameConst.NAME_PROCESS_3 -> {
                GroupEntity groupEntity = groupRepository.findById(receiverId)
                        .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
                jobEntity.setTeachers(List.of(sender));
                jobEntity.setGroups(groupEntity);
            }
        }

        jobEntity.setIsCompleted(0);
        jobEntity = jobRepository.save(jobEntity);

        return jobMapper.toDTO(jobEntity);
    }

    @Override
    public void deleteJobOfTeacherCompleted(String[] ids) {
        for (String id : ids) {
            jobRepository.deleteById(id);
        }
    }

    @Override
    public JobDTO markCompleted(String id) {
        JobEntity jobEntity = jobRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_EXIST));
        jobEntity.setIsCompleted(1);
        jobEntity = jobRepository.save(jobEntity);
        return jobMapper.toDTO(jobEntity);
    }

    @Override
    public List<JobDTO> findAll() {
        List<JobEntity> resultEntity = jobRepository.findAll();
        return resultEntity.stream().map(jobMapper::toDTO).toList();
    }


}
