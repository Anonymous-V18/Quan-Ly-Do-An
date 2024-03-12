package com.hcv.service;

import com.hcv.dto.TeacherDTO;
import com.hcv.dto.request.TeacherInput;

import java.util.List;

public interface ITeacherService {

    TeacherDTO insert(TeacherInput teacherInput);

    // void update(TeacherDTO old_teacherDTO, TeacherInput teacherInput);

    void delete(Long[] ids);

    List<TeacherDTO> showAll();

    TeacherDTO findOneByMaSo(String maSo);


}
