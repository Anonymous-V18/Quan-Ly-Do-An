package com.hcv.repository;

import com.hcv.entity.JobTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJobTeacherRepository extends JpaRepository<JobTeacher, String> {

    List<JobTeacher> findBySenderCode(String senderCode);

}
