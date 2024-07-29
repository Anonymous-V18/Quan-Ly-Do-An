package com.hcv.dto.response;

import com.hcv.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointResponse extends BaseDTO {

    private Double point;
    private String type;
    private String teacherId;

}
