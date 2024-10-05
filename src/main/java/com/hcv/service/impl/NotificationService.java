package com.hcv.service.impl;

import com.hcv.constant.NotificationTypeConst;
import com.hcv.constant.StatusNotificationConst;
import com.hcv.converter.INotificationMapper;
import com.hcv.dto.StatusNotification;
import com.hcv.dto.TypeNotification;
import com.hcv.dto.request.NotificationTypeInvitationInsertInput;
import com.hcv.dto.request.NotificationTypeReplyInvitationInput;
import com.hcv.dto.response.NotificationResponse;
import com.hcv.entity.Notification;
import com.hcv.entity.Student;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void insertInvitation(NotificationTypeInvitationInsertInput invitationInsertInput) {
        String currentUserId = userService.getClaimsToken().get("sub").toString().trim();
        Student studentEntity = studentRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        if (studentEntity.getGroup() == null) {
            throw new AppException(ErrorCode.STUDENT_HAS_NOT_GROUP);
        }

        String message = studentEntity.getName() + " muốn mời bạn vào nhóm !";
        String status = StatusNotificationConst.PENDING;
        String type = NotificationTypeConst.INVITATION;

        List<Student> studentList;
        if (Boolean.TRUE.equals(invitationInsertInput.getIsSendAllStudent())) {
            studentList = studentRepository
                    .findStudentToInvite(currentUserId, List.of(studentEntity.getSubject()), 0)
                    .stream().toList();
        } else {
            if (invitationInsertInput.getStudentIds().isEmpty()) {
                throw new AppException(ErrorCode.STUDENT_NOT_EXIST);
            }
            List<String> studentIds = invitationInsertInput.getStudentIds().stream()
                    .filter(studentId -> !studentId.trim().equals(currentUserId))
                    .distinct()
                    .toList();
            studentList = studentRepository.findAllById(studentIds);
            if (studentList.contains(null)) {
                throw new AppException(ErrorCode.STUDENT_NOT_EXIST);
            }
        }

        studentList = studentList.stream()
                .filter(studentEntity1 -> studentEntity1.getNotifications()
                        .stream()
                        .noneMatch(notificationEntity ->
                                notificationEntity.getStatus().equals(StatusNotification.valueOf(status))
                                        && notificationEntity.getType().equals(TypeNotification.valueOf(type))
                                        && notificationEntity.getSendFrom().equals(currentUserId)
                        )
                )
                .toList();

        List<Notification> invitationList = new ArrayList<>();
        for (Student student : studentList) {
            Notification invitationForm = new Notification();
            invitationForm.setMessage(message);
            invitationForm.setSendFrom(currentUserId);
            invitationForm.setStatus(StatusNotification.valueOf(status));
            invitationForm.setType(TypeNotification.valueOf(type));
            invitationForm.setSendTo(student.getId());
            invitationForm.setStudent(student);
            invitationList.add(invitationForm);
        }

        notificationRepository.saveAll(invitationList);
    }

    @Override
    public void replyInvitation(String id, NotificationTypeReplyInvitationInput replyInvitationInput) {
        Notification invitation = notificationRepository.findById(id)
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
