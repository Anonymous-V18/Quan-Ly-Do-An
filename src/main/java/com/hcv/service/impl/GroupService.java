package com.hcv.service.impl;

import com.hcv.converter.IGroupMapper;
import com.hcv.dto.request.GroupInput;
import com.hcv.dto.request.GroupInsertInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.GroupDTO;
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
    public GroupDTO insert(GroupInsertInput groupInsertInput) {
        String currentUserId = userService.getClaimsToken().get("sub").toString();

        StudentEntity studentEntity = studentRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        if (studentEntity.getGroups() != null) {
            throw new AppException(ErrorCode.STUDENT_EXISTED_IN_OTHER_GROUP);
        }

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setLeaderId(currentUserId);
        groupEntity.setMaxMember(groupInsertInput.getMaxMember());
        GroupEntity finalGroupEntity = groupEntity;
        studentEntity.setGroups(finalGroupEntity);

        groupEntity.setStudents(List.of(studentEntity));

        groupEntity = groupRepository.save(groupEntity);

        return mapper.toDTO(groupEntity);
    }

    @Override
    public void addMember(String leaderGroupId) {
        String currentUserId = userService.getClaimsToken().get("sub").toString();

        StudentEntity studentEntity = studentRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        if (studentEntity.getGroups() != null) {
            throw new AppException(ErrorCode.STUDENT_EXISTED_IN_OTHER_GROUP);
        }

        StudentEntity leaderGroup = studentRepository.findById(leaderGroupId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        String groupId = leaderGroup.getGroups().getId();
        GroupEntity groupEntity = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));
        if (groupEntity.getResearches() != null) {
            throw new AppException(ErrorCode.GROUP_NOT_CHANGE_MEMBER);
        }

        List<StudentEntity> oldStudentEntityList = groupEntity.getStudents();
        if (oldStudentEntityList.size() == groupEntity.getMaxMember()) {
            throw new AppException(ErrorCode.GROUP_ENOUGH_MEMBER);
        }

        studentEntity.setGroups(groupEntity);
        groupEntity.getStudents().add(studentEntity);

        groupEntity = groupRepository.save(groupEntity);

        mapper.toDTO(groupEntity);
    }

    @Override
    public void removeMember(String idOldGroup, GroupInput groupUpdateInput) {
        String currentUserId = userService.getClaimsToken().get("sub").toString();

        GroupEntity groupEntity = groupRepository.findById(idOldGroup)
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));

        if (!groupEntity.getLeaderId().equals(currentUserId)) {
            throw new AppException(ErrorCode.YOU_NOT_DELEGATE_LEADER);
        }

        List<StudentEntity> newMember = groupEntity.getStudents().stream()
                .filter(member -> member.getId().equals(currentUserId)
                        && !groupUpdateInput.getStudentIds().contains(member.getId())
                )
                .toList();

        List<StudentEntity> memberRemoveList = groupEntity.getStudents().stream()
                .filter(member -> !member.getId().equals(currentUserId)
                        && groupUpdateInput.getStudentIds().contains(member.getId())
                )
                .toList();

        memberRemoveList.forEach(member -> member.setGroups(null));
        studentRepository.saveAll(memberRemoveList);

        groupEntity.setStudents(newMember);
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
    public GroupResponse showInfoMyGroup() {
        String currentUserId = userService.getClaimsToken().get("sub").toString();
        StudentEntity studentEntity = studentRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));
        return mapper.toShowDTO(studentEntity.getGroups());
    }

    @Override
    public ShowAllResponse<GroupResponse> showAllMyGroup(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getPage();
        int limit = showAllRequest.getLimit();
        int totalPages = (int) Math.ceil((1.0 * countAll()) / limit);

        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );

        String currentUserId = userService.getClaimsToken().get("sub").toString();
        Page<GroupEntity> researchEntityList = groupRepository.findByResearches_Teachers_Id(currentUserId, paging);
        List<GroupEntity> resultEntity = researchEntityList.getContent();
        List<GroupResponse> resultDTO = resultEntity.stream().map(mapper::toShowDTO).toList();

        return ShowAllResponse.<GroupResponse>builder()
                .page(page)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

}
