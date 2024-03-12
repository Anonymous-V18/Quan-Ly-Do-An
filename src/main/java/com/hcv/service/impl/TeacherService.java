package com.hcv.service.impl;

import com.hcv.converter.ITeacherMapper;
import com.hcv.dto.TeacherDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.DepartmentEntity;
import com.hcv.entity.SubjectEntity;
import com.hcv.entity.TeacherEntity;
import com.hcv.entity.UserEntity;
import com.hcv.repository.IDepartmentRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.repository.IUserRepository;
import com.hcv.service.ITeacherService;
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
public class TeacherService implements ITeacherService {

    ITeacherRepository teacherRepository;
    ITeacherMapper teacherMapper;
    IUserRepository userRepository;
    IDepartmentRepository departmentRepository;
    ISubjectRepository subjectRepository;


    @Override
    public TeacherDTO insert(TeacherInput teacherInput) {

        TeacherEntity teacherEntity = teacherMapper.toEntity(teacherInput);

        UserEntity userEntity = userRepository.findOneById(teacherInput.getUser_id());
        teacherEntity.setUsers(userEntity);

        SubjectEntity subjectEntity = subjectRepository.findOneByName(teacherInput.getSubjectName());
        teacherEntity.setSubjects(subjectEntity);

        DepartmentEntity departmentEntity = subjectEntity.getDepartments();
        teacherEntity.setDepartments(departmentEntity);
        
        teacherRepository.save(teacherEntity);

        return teacherMapper.toDTO(teacherEntity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            teacherRepository.deleteById(id);
        }
    }

    @Override
    public int countAll() {
        return (int) teacherRepository.count();
    }

    @Override
    public ShowAllResponse<TeacherDTO> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getPage();
        int limit = showAllRequest.getLimit();
        int totalPages = (int) Math.ceil((1.0 * countAll()) / limit);

        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<TeacherEntity> teacherEntityList = teacherRepository.findAll(paging);

        List<TeacherEntity> resultEntity = teacherEntityList.getContent();

        List<TeacherDTO> resultDTO = resultEntity.stream().map(teacherMapper::toDTO).toList();

        return ShowAllResponse.<TeacherDTO>builder()
                .page(page)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public TeacherDTO findOneByMaSo(String maSo) {
        TeacherEntity teacher = teacherRepository.findOneByMaSo(maSo);
        return teacher == null ? null : teacherMapper.toDTO(teacher);
    }


}
