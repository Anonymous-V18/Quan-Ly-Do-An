package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackResponse {

    String id;
    String createdDate;
    String createdBy;
    String modifiedDate;
    String modifiedBy;
    String sendFrom;
    String sendFromName;
    String message;

}
