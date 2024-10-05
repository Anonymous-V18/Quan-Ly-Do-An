package com.hcv.repository;

import com.hcv.entity.JobTeacherDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJobTeacherDetailRepository extends JpaRepository<JobTeacherDetail, String> {

    List<JobTeacherDetail> findByTeacher_Id(String id);

}
