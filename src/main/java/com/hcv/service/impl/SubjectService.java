package com.hcv.service.impl;

import com.hcv.converter.ISubjectMapper;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.SubjectInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.SubjectDTO;
import com.hcv.dto.response.SubjectResponse;
import com.hcv.entity.DepartmentEntity;
import com.hcv.entity.SubjectEntity;
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
        subjectRepository.findByName(subjectInput.getName())
                .ifPresent(item -> {
                    throw new AppException(ErrorCode.SUBJECT_EXISTED);
                });

        SubjectEntity subjectEntity = subjectMapper.toEntity(subjectInput);
        DepartmentEntity departmentEntity = departmentRepository.findByName(subjectInput.getNameDepartment())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));

        subjectEntity.setDepartments(departmentEntity);
        subjectEntity = subjectRepository.save(subjectEntity);
        return subjectMapper.toDTO(subjectEntity);
    }

    @Override
    public SubjectDTO update(String oldSubjectId, SubjectInput subjectInput) {
        SubjectEntity subjectEntity = subjectRepository.findById(oldSubjectId)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));

        subjectEntity.setName(subjectInput.getName());

        DepartmentEntity departmentEntity = departmentRepository.findByName(subjectInput.getNameDepartment())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));

        subjectEntity.setDepartments(departmentEntity);

        subjectRepository.save(subjectEntity);

        return subjectMapper.toDTO(subjectEntity);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            subjectRepository.deleteById(id);
        }
    }

    @Override
    public SubjectDTO findOneById(String id) {
        SubjectEntity subjectEntity = subjectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
        return subjectMapper.toDTO(subjectEntity);
    }

    @Override
    public SubjectDTO findOneByName(String name) {
        SubjectEntity subjectEntity = subjectRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
        return subjectMapper.toDTO(subjectEntity);
    }

    @Override
    public int countAll() {
        return (int) subjectRepository.count();
    }

    @Override
    public ShowAllResponse<SubjectDTO> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getPage();
        int limit = showAllRequest.getLimit();
        int totalPages = (int) Math.ceil((1.0 * countAll()) / limit);

        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<SubjectEntity> subjectEntityList = subjectRepository.findAll(paging);
        List<SubjectEntity> resultEntity = subjectEntityList.getContent();
        List<SubjectDTO> resultDTO = resultEntity.stream().map(subjectMapper::toDTO).toList();

        return ShowAllResponse.<SubjectDTO>builder()
                .page(page)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public List<SubjectDTO> findAll() {
        List<SubjectEntity> resultEntity = subjectRepository.findAll();
        return resultEntity.stream().map(subjectMapper::toDTO).toList();
    }

    @Override
    public List<SubjectResponse> showAllByDepartment(String departmentId) {
        return subjectRepository.findByDepartments_Id(departmentId).stream().map(subjectMapper::toShowDTO).toList();
    }
}
