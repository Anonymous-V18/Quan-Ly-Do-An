package com.hcv.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowAllRequest {

    Integer currentPage;
    Integer limit;
    String orderBy;
    String orderDirection;

}
