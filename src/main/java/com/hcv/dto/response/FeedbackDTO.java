package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackDTO {

    String id;
    Date createdDate;
    Date modifiedDate;
    String createdBy;
    String modifiedBy;
    String message;
    String sendTo;
    String sendFrom;
    String sendFromName;

}
