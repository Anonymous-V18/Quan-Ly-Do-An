package com.hcv.service;

import com.hcv.dto.StudentDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.response.ShowAllResponse;

import java.util.List;

public interface IStudentService {

    StudentDTO insert(StudentInput studentInput);

    StudentDTO update(StudentDTO old_studentDTO, StudentInput studentInput);

    void delete(Long[] ids);

    StudentDTO findOneByMaSo(String maSo);

    int countAll();

    ShowAllResponse<StudentDTO> showAll(ShowAllRequest showAllRequest);

    List<StudentDTO> findAll();
}
