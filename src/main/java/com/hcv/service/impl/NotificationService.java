package com.hcv.service.impl;

import com.hcv.constant.NotificationTypeConst;
import com.hcv.constant.StatusNotificationConst;
import com.hcv.converter.INotificationMapper;
import com.hcv.dto.StatusNotification;
import com.hcv.dto.TypeNotification;
import com.hcv.dto.request.NotificationTypeInvitationInsertInput;
import com.hcv.dto.request.NotificationTypeReplyInvitationInput;
import com.hcv.dto.response.NotificationResponse;
import com.hcv.entity.NotificationEntity;
import com.hcv.entity.StudentEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.INotificationRepository;
import com.hcv.repository.IStudentRepository;
import com.hcv.service.IGroupService;
import com.hcv.service.INotificationService;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService implements INotificationService {

    INotificationRepository notificationRepository;
    INotificationMapper notificationMapper;
    IUserService userService;
    IStudentRepository studentRepository;
    IGroupService groupService;

    @Override
    public void insertInvitation(NotificationTypeInvitationInsertInput invitationInsertInput) {
        String currentUserId = userService.getClaimsToken().get("sub").toString();
        StudentEntity studentEntity = studentRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        String message = studentEntity.getName() + " muốn mời bạn vào nhóm !";
        String status = StatusNotificationConst.PENDING;
        String type = NotificationTypeConst.INVITATION;


        List<StudentEntity> studentEntityList;
        if (invitationInsertInput.getIsSendAllStudent()) {
            studentEntityList = studentRepository.findByUsers_IsGraduate(1).stream()
                    .filter(studentEntity1 -> !studentEntity1.getId().equals(currentUserId))
                    .toList();
        } else {
            studentEntityList = studentRepository.findAllById(invitationInsertInput.getStudentIds());
            if (studentEntityList.contains(null)) {
                throw new AppException(ErrorCode.STUDENT_NOT_EXIST);
            }
        }

        List<NotificationEntity> invitationList = new ArrayList<>();
        for (StudentEntity student : studentEntityList) {
            NotificationEntity invitationForm = new NotificationEntity();
            invitationForm.setMessage(message);
            invitationForm.setSendFrom(currentUserId);
            invitationForm.setStatus(StatusNotification.valueOf(status));
            invitationForm.setType(TypeNotification.valueOf(type));
            invitationForm.setSendTo(student.getId());
            invitationForm.setStudents(student);
            invitationList.add(invitationForm);
        }

        notificationRepository.saveAll(invitationList);
    }

    @Override
    public void replyInvitation(String id, NotificationTypeReplyInvitationInput replyInvitationInput) {
        NotificationEntity invitation = notificationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVITATION_NOT_EXIST));

        String newStatusInvitation = replyInvitationInput.getStatusInvitation();
        invitation.setStatus(StatusNotification.valueOf(newStatusInvitation));

        if (newStatusInvitation.equals("AOS")) {
            groupService.addMember(invitation.getSendFrom());
        }
    }

    @Override
    public List<NotificationResponse> showAllInvitation() {
        String currentUserId = userService.getClaimsToken().get("sub").toString();
        String type = NotificationTypeConst.INVITATION;
        return notificationRepository.findBySendToAndType(currentUserId, TypeNotification.valueOf(type))
                .stream()
                .map(notificationMapper::toShowDTO)
                .toList();
    }
}
