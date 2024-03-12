package com.hcv.service;

import com.hcv.dto.SubjectDTO;
import com.hcv.dto.request.SubjectInput;

import java.util.List;

public interface ISubjectService {

    SubjectDTO insert(SubjectInput subjectInput);

    SubjectDTO update(SubjectDTO old_subjectDTO, SubjectInput subjectInput);

    void delete(Long[] ids);

    SubjectDTO findOneById(Long id);

    SubjectDTO findOneByName(String name);

    List<SubjectDTO> showAll();
}
