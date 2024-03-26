package com.hcv.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowAllRequest {


    private Integer page;
    private Integer limit;
    private String orderBy;
    private String orderDirection;

}
