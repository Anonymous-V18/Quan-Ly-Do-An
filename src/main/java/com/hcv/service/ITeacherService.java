package com.hcv.service;

import com.hcv.dto.TeacherDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.response.ShowAllResponse;

public interface ITeacherService {

    TeacherDTO insert(TeacherInput teacherInput);

    // void update(TeacherDTO old_teacherDTO, TeacherInput teacherInput);

    void delete(Long[] ids);

    int countAll();

    ShowAllResponse<TeacherDTO> showAll(ShowAllRequest showAllRequest);

    TeacherDTO findOneByMaSo(String maSo);


}
