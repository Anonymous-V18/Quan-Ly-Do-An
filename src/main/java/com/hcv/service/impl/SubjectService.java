package com.hcv.service.impl;

import com.hcv.converter.ISubjectMapper;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.SubjectInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.SubjectDTO;
import com.hcv.dto.response.SubjectResponse;
import com.hcv.entity.Department;
import com.hcv.entity.Subject;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IDepartmentRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.service.ISubjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectService implements ISubjectService {

    ISubjectRepository subjectRepository;
    ISubjectMapper subjectMapper;
    IDepartmentRepository departmentRepository;

    @Override
    public SubjectDTO insert(SubjectInput subjectInput) {
        subjectRepository.findById(subjectInput.getName())
                .ifPresent(item -> {
                    throw new AppException(ErrorCode.SUBJECT_EXISTED);
                });

        Subject subject = subjectMapper.toEntity(subjectInput);
        Department department = departmentRepository.findById(subjectInput.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));

        subject.setDepartment(department);
        subject = subjectRepository.save(subject);
        return subjectMapper.toDTO(subject);
    }

    @Override
    public SubjectDTO update(String oldSubjectId, SubjectInput subjectInput) {
        Subject subject = subjectRepository.findById(oldSubjectId)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));

        subject.setName(subjectInput.getName());

        Department department = departmentRepository.findById(subjectInput.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));

        subject.setDepartment(department);
        
        subjectRepository.save(subject);

        return subjectMapper.toDTO(subject);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            subjectRepository.deleteById(id);
        }
    }

    @Override
    public SubjectDTO findOneById(String id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
        return subjectMapper.toDTO(subject);
    }

    @Override
    public SubjectDTO findOneByName(String name) {
        Subject subject = subjectRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
        return subjectMapper.toDTO(subject);
    }

    @Override
    public int count() {
        return (int) subjectRepository.count();
    }

    @Override
    public ShowAllResponse<SubjectDTO> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getCurrentPage();
        int limit = showAllRequest.getLimit();
        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<Subject> subjectEntityList = subjectRepository.findAll(paging);
        List<SubjectDTO> resultDTO = subjectEntityList.getContent().stream().map(subjectMapper::toDTO).toList();

        int totalElements = (int) subjectRepository.count();
        int totalPages = (int) Math.ceil((1.0 * totalElements) / limit);

        return ShowAllResponse.<SubjectDTO>builder()
                .currentPage(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public List<SubjectDTO> findAll() {
        List<Subject> resultEntity = subjectRepository.findAll();
        return resultEntity.stream().map(subjectMapper::toDTO).toList();
    }

    @Override
    public List<SubjectResponse> showAllByDepartment(String departmentId) {
        return subjectRepository.findByDepartment_Id(departmentId).stream().map(subjectMapper::toShowDTO).toList();
    }
}
