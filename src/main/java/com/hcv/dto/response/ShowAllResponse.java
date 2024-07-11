package com.hcv.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class ShowAllResponse<T> {
    private int page;
    private int totalPages;
    private List<T> responses;
}
