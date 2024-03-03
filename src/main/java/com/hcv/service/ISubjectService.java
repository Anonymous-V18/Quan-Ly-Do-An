package com.hcv.service;

import com.hcv.api_controller.input.SubjectInput;
import com.hcv.dto.SubjectDTO;

import java.util.List;

public interface ISubjectService {

    SubjectDTO insert(SubjectInput subjectInput);

    SubjectDTO update(SubjectDTO old_subjectDTO, SubjectInput subjectInput);

    void delete(Long[] ids);

    SubjectDTO findOneById(Long id);

    SubjectDTO findOneByName(String name);

    List<SubjectDTO> showAll();
}
