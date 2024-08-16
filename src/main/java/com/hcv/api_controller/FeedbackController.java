package com.hcv.api_controller;

import com.hcv.dto.request.FeedbackForResearchInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.FeedbackDTO;
import com.hcv.service.IFeedbackService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/feedbacks")
public class FeedbackController {

    IFeedbackService feedbackService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<FeedbackDTO> insert(@RequestBody @Valid FeedbackForResearchInput feedbackForResearchInput) {
        FeedbackDTO response = feedbackService.insert(feedbackForResearchInput);
        return ApiResponse.<FeedbackDTO>builder()
                .result(response)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<FeedbackDTO> update(@PathVariable(value = "id") String id,
                                           @RequestBody @Valid FeedbackForResearchInput feedbackForResearchInput) {

        FeedbackDTO response = feedbackService.update(id, feedbackForResearchInput);
        return ApiResponse.<FeedbackDTO>builder()
                .result(response)
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
