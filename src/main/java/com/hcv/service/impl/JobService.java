package com.hcv.service.impl;

import com.hcv.converter.IJobMapper;
import com.hcv.dto.JobDTO;
import com.hcv.dto.request.JobForTeacherInput;
import com.hcv.entity.JobEntity;
import com.hcv.entity.TeacherEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IJobRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.service.IJobService;
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

    @Override
    public JobDTO insert(JobForTeacherInput jobForTeacherInput) {
        JobEntity jobEntity = jobMapper.toEntity(jobForTeacherInput);

        String sendTo = jobForTeacherInput.getSendTo();
        TeacherEntity teacherEntity = teacherRepository.findOneByMaSo(sendTo);
        if (teacherEntity == null) {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }
        jobEntity.setTeachers(List.of(teacherEntity));

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
