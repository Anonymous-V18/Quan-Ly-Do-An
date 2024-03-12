package com.hcv.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShowAllRequest {

    @NotNull(message = "INVALID_PAGE_PARAM")
    @Min(value = 1, message = "INVALID_PAGE_PARAM")
    private Integer page;
    @NotNull(message = "INVALID_LIMIT_PARAM")
    @Min(value = 1, message = "INVALID_LIMIT_PARAM")
    private Integer limit;
    @NotNull(message = "INVALID_ORDER_BY_PARAM")
    @Size(min = 2, message = "INVALID_ORDER_BY_PARAM")
    private String orderBy;
    @NotNull(message = "INVALID_ORDER_DIRECTION_PARAM")
    @Size(min = 3, max = 4, message = "INVALID_ORDER_DIRECTION_PARAM")
    private String orderDirection;

}
