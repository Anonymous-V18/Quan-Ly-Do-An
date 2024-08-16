package com.hcv.api_controller;

import com.hcv.dto.request.NotificationTypeInvitationInsertInput;
import com.hcv.dto.request.NotificationTypeReplyInvitationInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.NotificationResponse;
import com.hcv.service.INotificationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/notifications")
public class NotificationController {

    INotificationService notificationService;

    @PostMapping("/insert-invitation")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<String> insertInvitation(@RequestBody @Valid NotificationTypeInvitationInsertInput invitationInsertInput) {
        notificationService.insertInvitation(invitationInsertInput);
        return ApiResponse.<String>builder()
                .message("Gửi lời mời vào nhóm thành công !")
                .build();
    }

    @PutMapping("/reply-invitation/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<String> replyInvitation(@PathVariable(value = "id") String id,
                                               @RequestBody @Valid NotificationTypeReplyInvitationInput replyInvitationInput) {
        notificationService.replyInvitation(id, replyInvitationInput);
        return ApiResponse.<String>builder()
                .message("Phản hồi lời mời thành công !")
                .build();
    }

    @GetMapping("/showAll-invitation")
    public ApiResponse<List<NotificationResponse>> showAllInvitation() {
        List<NotificationResponse> response = notificationService.showAllInvitation();
        return ApiResponse.<List<NotificationResponse>>builder()
                .result(response)
                .build();
    }

}
