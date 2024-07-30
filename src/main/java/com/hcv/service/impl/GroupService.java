package com.hcv.service.impl;

import com.hcv.converter.IGroupMapper;
import com.hcv.dto.GroupDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.group.GroupInput;
import com.hcv.dto.response.GroupResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.GroupEntity;
import com.hcv.entity.StudentEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IGroupRepository;
import com.hcv.repository.IStudentRepository;
import com.hcv.service.IGroupService;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GroupService implements IGroupService {

    IGroupRepository groupRepository;
    IGroupMapper mapper;
    IStudentRepository studentRepository;
    IUserService userService;

    @Override
    public GroupDTO insert(GroupInput groupInput) {
        String currentUser = userService.getSubToken();
        List<String> studentID = groupInput.getStudentId();
        if (!studentID.contains(currentUser)) {
            studentID.add(currentUser);
        }

        List<StudentEntity> studentEntityList = studentRepository.findAllById(studentID);
        if (studentEntityList.contains(null)) {
            throw new AppException(ErrorCode.STUDENT_NOT_EXIST);
        }

        this.checkStudentExistedInOtherGroup(studentEntityList);

        GroupEntity groupEntity = new GroupEntity();

        GroupEntity finalGroupEntity = groupEntity;
        studentEntityList.forEach(s -> s.setGroups(finalGroupEntity));
        groupEntity.setStudents(studentEntityList);

        groupEntity = groupRepository.save(groupEntity);

        return mapper.toDTO(groupEntity);
    }

    @Override
    public GroupDTO update(String oldGroupId, GroupInput newGroupInput) {
        GroupEntity oldGroupEntity = groupRepository.findOneById(oldGroupId);
        if (oldGroupEntity.getResearches() != null) {
            throw new AppException(ErrorCode.GROUP_NOT_CHANGE_MEMBER);
        }

        List<StudentEntity> oldStudentEntityList = oldGroupEntity.getStudents();
        this.checkStudentExistedInOtherGroup(oldStudentEntityList);
        oldStudentEntityList.forEach(studentEntity -> studentEntity.setGroups(null));
        studentRepository.saveAll(oldStudentEntityList);

        List<String> newStudentIDList = newGroupInput.getStudentId();
        List<StudentEntity> newStudentEntityList = studentRepository.findAllById(newStudentIDList);
        oldGroupEntity.setStudents(newStudentEntityList);

        oldGroupEntity = groupRepository.save(oldGroupEntity);

        return mapper.toDTO(oldGroupEntity);
    }

    @Override
    public void delete(String[] ids) {
        List<StudentEntity> studentEntityList = studentRepository.findByGroups_IdIn(Arrays.stream(ids).toList());
        if (!studentEntityList.isEmpty()) {
            studentEntityList.forEach(studentEntity -> studentEntity.setGroups(null));
            studentRepository.saveAll(studentEntityList);
        }
        groupRepository.deleteAllById(Arrays.stream(ids).toList());
    }

    @Override
    public int countAll() {
        return (int) groupRepository.count();
    }

    @Override
    public GroupDTO findOneById(String id) {
        GroupEntity groupEntity = groupRepository.findOneById(id);
        return groupEntity == null ? null : mapper.toDTO(groupEntity);
    }

    @Override
    public ShowAllResponse<GroupResponse> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getPage();
        int limit = showAllRequest.getLimit();
        int totalPages = (int) Math.ceil((1.0 * countAll()) / limit);

        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<GroupEntity> researchEntityList = groupRepository.findAll(paging);
        List<GroupEntity> resultEntity = researchEntityList.getContent();
        List<GroupResponse> resultDTO = resultEntity.stream().map(mapper::toShowDTO).toList();

        return ShowAllResponse.<GroupResponse>builder()
                .page(page)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    private void checkStudentExistedInOtherGroup(List<StudentEntity> studentEntityList) {
        List<StudentEntity> studentEntityListExistedInOtherGroup = studentEntityList.stream()
                .filter(studentEntity -> studentEntity.getGroups() != null)
                .toList();
        if (!studentEntityListExistedInOtherGroup.isEmpty()) {
            throw new AppException(ErrorCode.STUDENT_EXISTED_IN_OTHER_GROUP);
        }
    }
}
