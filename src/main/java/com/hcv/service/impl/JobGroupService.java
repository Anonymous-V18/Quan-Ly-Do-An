package com.hcv.service.impl;

import com.hcv.converter.IJobMapper;
import com.hcv.dto.TypeTeacherEnum;
import com.hcv.dto.request.JobGroupInput;
import com.hcv.dto.response.JobGroupResponse;
import com.hcv.entity.Group;
import com.hcv.entity.JobGroup;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IGroupRepository;
import com.hcv.repository.IJobGroupRepository;
import com.hcv.service.IJobGroupService;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobGroupService implements IJobGroupService {

    IJobGroupRepository jobGroupRepository;
    IJobMapper jobMapper;
    IUserService userService;
    IGroupRepository groupRepository;


    @Override
    public JobGroupResponse insert(JobGroupInput jobInput) {
        JobGroup jobGroup = jobMapper.toEntity(jobInput);

        String currentUserId = userService.getClaimsToken().get("sub").toString();
        jobGroup.setSenderId(currentUserId);

        String groupId = jobInput.getGroupId();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));

        if (group.getResearch() == null) {
            throw new AppException(ErrorCode.GROUP_HAS_NOT_REGISTERED_RESEARCH);
        }

        boolean isInstructorOfGroup = Objects.requireNonNullElseGet(group.getResearch(), () -> {
                    throw new AppException(ErrorCode.GROUP_HAS_NOT_REGISTERED_RESEARCH);
                })
                .getResearchTeachers()
                .stream()
                .anyMatch(researchTeacher -> researchTeacher.getTeacher().getId().equals(currentUserId)
                        && researchTeacher.getTypeTeacher().getCode().equals(TypeTeacherEnum.INSTRUCTOR.name())
                );
        if (!isInstructorOfGroup) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        jobGroup.setGroup(group);

        jobGroup.setIsCompleted(0);
        jobGroup = jobGroupRepository.save(jobGroup);
        return jobMapper.toDTO(jobGroup);
    }

    @Override
    public JobGroupResponse update(String oldJobId, JobGroupInput jobInput) {
        JobGroup jobGroup = jobGroupRepository.findById(oldJobId)
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));
        jobGroup = jobMapper.toEntity(jobGroup, jobInput);
        jobGroup = jobGroupRepository.save(jobGroup);
        return jobMapper.toDTO(jobGroup);
    }

    @Override
    public void markCompleted(String groupId) {
        JobGroup jobGroup = jobGroupRepository.findById(groupId)
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));
        jobGroup.setIsCompleted(1);
        jobGroupRepository.save(jobGroup);
    }

    @Override
    public void delete(String[] jobId) {
        Arrays.stream(jobId).toList().forEach(jobGroupRepository::deleteById);
    }

    @Override
    public JobGroupResponse findById(String groupId) {
        JobGroup jobGroup = jobGroupRepository.findById(groupId)
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));
        return jobMapper.toDTO(jobGroup);
    }


}
