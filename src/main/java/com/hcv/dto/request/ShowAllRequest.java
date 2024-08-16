package com.hcv.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowAllRequest {

    Integer page;
    Integer limit;
    String orderBy;
    String orderDirection;

}
