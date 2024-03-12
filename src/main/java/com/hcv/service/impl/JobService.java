package com.hcv.service.impl;

import com.hcv.converter.IJobMapper;
import com.hcv.dto.JobDTO;
import com.hcv.dto.request.JobForTeacherInput;
import com.hcv.entity.JobEntity;
import com.hcv.entity.TeacherEntity;
import com.hcv.entity.UserEntity;
import com.hcv.repository.*;
import com.hcv.service.IJobService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobService implements IJobService {

    IJobRepository jobRepository;
    IJobMapper jobMapper;
    ITeacherRepository teacherRepository;
    ISubjectRepository subjectRepository;
    IUserRepository userRepository;
    IDepartmentRepository departmentRepository;

    @Override
    public JobDTO insert(JobForTeacherInput jobForTeacherInput) {
        JobEntity jobEntity = jobMapper.toEntity(jobForTeacherInput);
        String sendTo = jobForTeacherInput.getSendTo();
        if (sendTo.length() < 8) {
            String sendFrom = jobForTeacherInput.getSendFrom();
            UserEntity user = userRepository.findOneByUsername(sendFrom);
            List<TeacherEntity> teacherEntityList = teacherRepository.findAllByChucVuAndDepartments("TRƯỞNG BỘ MÔN", user.getTeachers().getDepartments());
            jobEntity.setTeachers(teacherEntityList);
            jobRepository.save(jobEntity);
            return jobMapper.toDTO(jobEntity);
        }
        TeacherEntity teacherEntity = teacherRepository.findOneByMaSo(sendTo);
        jobEntity.setTeachers(List.of(teacherEntity));
        jobRepository.save(jobEntity);
        return jobMapper.toDTO(jobEntity);
    }

    @Override
    public void deleteJobOfTeacherCompleted(Long[] ids) {
        for (Long id : ids) {
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
        List<JobDTO> resultDTO = new ArrayList<>();
        for (JobEntity jobEntity : resultEntity) {
            JobDTO jobDTO = jobMapper.toDTO(jobEntity);
            resultDTO.add(jobDTO);
        }
        return resultDTO;
    }

}
