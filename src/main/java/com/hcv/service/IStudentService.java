package com.hcv.service;

import com.hcv.dto.StudentDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.response.ShowAllResponse;

public interface IStudentService {

    StudentDTO insert(StudentInput studentInput);

    void delete(Long[] ids);

    StudentDTO findOneByMaSo(String maSo);

    int countAll();

    ShowAllResponse<StudentDTO> showAll(ShowAllRequest showAllRequest);
}
