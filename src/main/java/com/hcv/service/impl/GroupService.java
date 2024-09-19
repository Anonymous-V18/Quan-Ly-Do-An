package com.hcv.service.impl;

import com.hcv.converter.IGroupMapper;
import com.hcv.dto.request.GroupInput;
import com.hcv.dto.request.GroupInsertInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.GroupDTO;
import com.hcv.dto.response.GroupResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.Group;
import com.hcv.entity.Student;
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

        Student student = studentRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        if (student.getGroups() != null) {
            throw new AppException(ErrorCode.STUDENT_EXISTED_IN_OTHER_GROUP);
        }

        Group group = new Group();
        group.setLeaderId(currentUserId);
        group.setMaxMember(groupInsertInput.getMaxMember());
        Group finalGroup = group;
        student.setGroups(finalGroup);

        group.setStudents(List.of(student));

        group = groupRepository.save(group);

        return mapper.toDTO(group);
    }

    @Override
    public void addMember(String leaderGroupId) {
        String currentUserId = userService.getClaimsToken().get("sub").toString();

        Student student = studentRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        if (student.getGroups() != null) {
            throw new AppException(ErrorCode.STUDENT_EXISTED_IN_OTHER_GROUP);
        }

        Student leaderGroup = studentRepository.findById(leaderGroupId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        if (leaderGroup.getGroups() == null) {
            throw new AppException(ErrorCode.LEADER_HAS_NOT_GROUP);
        }

        String groupId = leaderGroup.getGroups().getId();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));
        if (group.getResearches() != null) {
            throw new AppException(ErrorCode.GROUP_NOT_CHANGE_MEMBER);
        }

        List<Student> oldStudentList = group.getStudents();
        if (oldStudentList.size() == group.getMaxMember()) {
            throw new AppException(ErrorCode.GROUP_ENOUGH_MEMBER);
        }

        student.setGroups(group);
        group.getStudents().add(student);

        group = groupRepository.save(group);

        mapper.toDTO(group);
    }

    @Override
    public void removeMember(String idOldGroup, GroupInput groupUpdateInput) {
        String currentUserId = userService.getClaimsToken().get("sub").toString();

        Group group = groupRepository.findById(idOldGroup)
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));

        if (!group.getLeaderId().equals(currentUserId)) {
            throw new AppException(ErrorCode.YOU_NOT_DELEGATE_LEADER);
        }

        List<Student> newMember = group.getStudents().stream()
                .filter(member -> member.getId().equals(currentUserId)
                        && !groupUpdateInput.getStudentIds().contains(member.getId())
                )
                .toList();

        List<Student> memberRemoveList = group.getStudents().stream()
                .filter(member -> !member.getId().equals(currentUserId)
                        && groupUpdateInput.getStudentIds().contains(member.getId())
                )
                .toList();

        memberRemoveList.forEach(member -> member.setGroups(null));
        studentRepository.saveAll(memberRemoveList);

        group.setStudents(newMember);
    }

    @Override
    public void delete(String[] ids) {
        List<Student> studentList = studentRepository.findByGroups_IdIn(Arrays.stream(ids).toList());
        if (!studentList.isEmpty()) {
            studentList.forEach(studentEntity -> studentEntity.setGroups(null));
            studentRepository.saveAll(studentList);
        }
        groupRepository.deleteAllById(Arrays.stream(ids).toList());
    }


    @Override
    public GroupResponse showInfoMyGroup() {
        String currentUserId = userService.getClaimsToken().get("sub").toString();
        Student student = studentRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));
        return mapper.toShowDTO(student.getGroups());
    }

    @Override
    public int countByResearches_Teachers_Id(String currentUserId) {
        return (int) groupRepository.countByResearches_Teachers_Id(currentUserId);
    }

    @Override
    public ShowAllResponse<GroupResponse> showAllMyGroup(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getCurrentPage();
        int limit = showAllRequest.getLimit();
        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );

        String currentUserId = userService.getClaimsToken().get("sub").toString();
        Page<Group> researchEntityList = groupRepository.findByResearches_Teachers_Id(currentUserId, paging);
        List<GroupResponse> resultDTO = researchEntityList.getContent().stream().map(mapper::toShowDTO).toList();

        int totalElements = this.countByResearches_Teachers_Id(currentUserId);
        int totalPages = (int) Math.ceil((1.0 * totalElements) / limit);

        return ShowAllResponse.<GroupResponse>builder()
                .currentPage(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

}
