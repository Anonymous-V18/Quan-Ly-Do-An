package com.hcv.service;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.TeacherFromExcelInput;
import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.TeacherDTO;

import java.util.List;

public interface ITeacherService {

    void checkDataBeforeInsert(TeacherFromExcelInput teacherFromExcelInput);

    TeacherDTO insert(TeacherInput teacherInput);

    TeacherDTO update(String oldTeacherId, TeacherInput teacherInput);

    void delete(String[] ids);

    int countAll();

    ShowAllResponse<TeacherDTO> showAll(ShowAllRequest showAllRequest);

    TeacherDTO findOneByMaSo(String maSo);

    TeacherDTO findOneById(String id);

    List<TeacherDTO> findAll();

}
