package com.hcv.service;

import com.hcv.api_controller.input.StudentInput;
import com.hcv.dto.StudentDTO;

import java.util.List;

public interface IStudentService {

    void insert(StudentInput studentInput);

    void delete(Long[] ids);

    StudentDTO findOneByMaSo(String maSo);

    List<StudentDTO> showAll();
}
