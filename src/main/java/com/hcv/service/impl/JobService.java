package com.hcv.service.impl;

import com.hcv.api_controller.input.JobForTeacherInput;
import com.hcv.converter.JobConverter;
import com.hcv.dto.JobDTO;
import com.hcv.entity.JobEntity;
import com.hcv.entity.SubjectEntity;
import com.hcv.entity.TeacherEntity;
import com.hcv.repository.IJobRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService implements IJobService {

    @Autowired
    private IJobRepository jobRepository;
    @Autowired
    private JobConverter jobConverter;

    @Autowired
    private ITeacherRepository teacherRepository;
    @Autowired
    private ISubjectRepository subjectRepository;

    @Override
    public JobDTO insert(JobForTeacherInput jobForTeacherInput) {
        JobEntity jobEntity = jobConverter.toEntity(jobForTeacherInput);
        if (jobForTeacherInput.getListMaSoGV().isEmpty()) {
            String subjectName = jobForTeacherInput.getSendTo();
            SubjectEntity subjectEntity = subjectRepository.findOneByName(subjectName);
            TeacherEntity teacherEntity = teacherRepository.findOneByChucVuAndSubjects("TRƯỞNG BỘ MÔN", subjectEntity);
            jobEntity.setTeachers(List.of(teacherEntity));
        } else {
            List<TeacherEntity> teachers = new ArrayList<>();
            for (String maSoGV : jobForTeacherInput.getListMaSoGV()) {
                TeacherEntity teacherEntity = teacherRepository.findOneByMaSo(maSoGV);
                teachers.add(teacherEntity);
            }
            jobEntity.setTeachers(teachers);
        }
        jobRepository.save(jobEntity);
        return jobConverter.toDTO(jobEntity);
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
            JobDTO jobDTO = jobConverter.toDTO(jobEntity);
            resultDTO.add(jobDTO);
        }
        return resultDTO;
    }

}
