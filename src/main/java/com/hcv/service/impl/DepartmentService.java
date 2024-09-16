package com.hcv.service.impl;

import com.hcv.converter.IDepartmentMapper;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.DepartmentDTO;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.Department;
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
        departmentRepository.findByName(departmentDTO.getName())
                .ifPresent(item -> {
                    throw new AppException(ErrorCode.DEPARTMENT_EXISTED);
                });

        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDTO(department);
    }

    @Override
    public DepartmentDTO update(String oldDepartmentId, DepartmentDTO newDepartmentDTO) {
        Department departmentUpdate =
                departmentRepository.findById(oldDepartmentId)
                        .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));

        departmentUpdate.setName(newDepartmentDTO.getName());

        departmentRepository.save(departmentUpdate);

        return departmentMapper.toDTO(departmentUpdate);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            departmentRepository.deleteById(id);
        }
    }

    @Override
    public DepartmentDTO findOneByName(String name) {
        Department department = departmentRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));
        return departmentMapper.toDTO(department);
    }

    @Override
    public ShowAllResponse<DepartmentDTO> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getCurrentPage();
        int limit = showAllRequest.getLimit();
        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<Department> departmentEntityList = departmentRepository.findAll(paging);
        List<DepartmentDTO> resultDTO = departmentEntityList.getContent().stream().map(departmentMapper::toDTO).toList();

        int totalElements = resultDTO.size();
        int totalPages = (int) Math.ceil((1.0 * totalElements) / limit);

        return ShowAllResponse.<DepartmentDTO>builder()
                .currentPage(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public List<DepartmentDTO> findAll() {
        List<Department> resultEntity = departmentRepository.findAll();
        return resultEntity.stream().map(departmentMapper::toDTO).toList();
    }
}
