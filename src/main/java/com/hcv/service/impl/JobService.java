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

        String teacherId = userService.getSubToken();
        TeacherEntity sender = teacherRepository.findOneById(teacherId);
        jobEntity.setSendFrom(teacherId);

        String receiverId = jobInput.getSendTo();
        Object receiver = null;
        switch (jobInput.getName().trim()) {

            case JobNameConst.NAME_PROCESS_1,
                 JobNameConst.NAME_PROCESS_2 -> {
                TeacherEntity teacherEntity = teacherRepository.findOneById(receiverId);
                receiver = teacherEntity;
                jobEntity.setTeachers(List.of(teacherEntity, sender));
            }
            case JobNameConst.NAME_PROCESS_3 -> {
                GroupEntity groupEntity = groupRepository.findOneById(receiverId);
                receiver = groupEntity;
                jobEntity.setTeachers(List.of(sender));
                jobEntity.setGroups(groupEntity);
            }
        }

        if (receiver == null) {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }

        jobRepository.save(jobEntity);

        return jobMapper.toDTO(jobEntity);
    }

    @Override
    public void deleteJobOfTeacherCompleted(String[] ids) {
        for (String id : ids) {
            jobRepository.deleteById(id);
        }
    }


    @Override
    public List<JobDTO> showAllJobNotComplete(String maSoGV) {
        TeacherEntity teacherEntity = teacherRepository.findOneByMaSo(maSoGV);
        if (teacherEntity.getJobs() == null) {
            return null;
        }
        List<JobEntity> resultEntity = teacherEntity.getJobs();

        return resultEntity.stream().map(jobMapper::toDTO).toList();
    }

    @Override
    public List<JobDTO> findAll() {
        List<JobEntity> resultEntity = jobRepository.findAll();
        return resultEntity.stream().map(jobMapper::toDTO).toList();
    }


}
