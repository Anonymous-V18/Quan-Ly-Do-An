package com.hcv.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ShowAllResponse<T> {
    private int page;
    private int totalPages;
    private List<T> responses;
}
