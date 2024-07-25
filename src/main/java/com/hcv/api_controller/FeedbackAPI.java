package com.hcv.api_controller;

import com.hcv.dto.FeedbackDTO;
import com.hcv.dto.request.FeedbackForResearchInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.service.IFeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/feedbacks")
public class FeedbackAPI {

    IFeedbackService feedbackService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<FeedbackDTO> insert(@RequestBody FeedbackForResearchInput feedbackForResearchInput) {
        FeedbackDTO newResearch = feedbackService.insert(feedbackForResearchInput);
        return ApiResponse.<FeedbackDTO>builder()
                .result(newResearch)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<FeedbackDTO> update(@PathVariable(value = "id") String id,
                                           @RequestBody FeedbackForResearchInput feedbackForResearchInput) {
        FeedbackDTO oldFeedbackDTO = feedbackService.findOneById(id);
        FeedbackDTO updateDTO;
        if (oldFeedbackDTO == null) {
            updateDTO = feedbackService.insert(feedbackForResearchInput);
        } else {
            updateDTO = feedbackService.update(oldFeedbackDTO, feedbackForResearchInput);
        }
        return ApiResponse.<FeedbackDTO>builder()
                .result(updateDTO)
                .build();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<String> delete(@RequestBody String[] ids) {
        feedbackService.delete(ids);
        return ApiResponse.<String>builder()
                .message("Xóa phản hồi thành công !")
                .build();
    }
}
