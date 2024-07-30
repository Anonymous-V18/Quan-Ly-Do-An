package com.hcv.service.impl;

import com.hcv.converter.IDepartmentMapper;
import com.hcv.dto.DepartmentDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.DepartmentEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IDepartmentRepository;
import com.hcv.service.IDepartmentService;
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
public class DepartmentService implements IDepartmentService {

    IDepartmentRepository departmentRepository;
    IDepartmentMapper departmentMapper;

    @Override
    public DepartmentDTO insert(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = departmentRepository.findOneByName(departmentDTO.getName());
        if (departmentEntity != null) {
            throw new AppException(ErrorCode.DEPARTMENT_EXISTED);
        }
        departmentEntity = departmentMapper.toEntity(departmentDTO);
        departmentRepository.save(departmentEntity);
        return departmentDTO;
    }

    @Override
    public DepartmentDTO update(String oldDepartmentId, DepartmentDTO newDepartmentDTO) {
        DepartmentEntity departmentEntityUpdate = departmentRepository.findOneById(oldDepartmentId);
        if (departmentEntityUpdate == null) {
            throw new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED);
        }
        departmentEntityUpdate.setName(newDepartmentDTO.getName());

        departmentRepository.save(departmentEntityUpdate);

        return departmentMapper.toDTO(departmentEntityUpdate);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            departmentRepository.deleteById(id);
        }
    }

    @Override
    public DepartmentDTO findOneByName(String name) {
        DepartmentEntity departmentEntity = departmentRepository.findOneByName(name);
        return departmentEntity == null ? null : departmentMapper.toDTO(departmentEntity);
    }

    @Override
    public int countAll() {
        return (int) departmentRepository.count();
    }

    @Override
    public ShowAllResponse<DepartmentDTO> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getPage();
        int limit = showAllRequest.getLimit();
        int totalPages = (int) Math.ceil((1.0 * countAll()) / limit);

        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<DepartmentEntity> departmentEntityList = departmentRepository.findAll(paging);
        List<DepartmentEntity> resultEntity = departmentEntityList.getContent();
        List<DepartmentDTO> resultDTO = resultEntity.stream().map(departmentMapper::toDTO).toList();

        return ShowAllResponse.<DepartmentDTO>builder()
                .page(page)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public List<DepartmentDTO> findAll() {
        List<DepartmentEntity> resultEntity = departmentRepository.findAll();
        return resultEntity.stream().map(departmentMapper::toDTO).toList();
    }
}
