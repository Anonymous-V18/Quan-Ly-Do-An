package com.hcv.dto.response;

import com.hcv.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class GroupResponse extends BaseDTO {

    private List<StudentResponse> students = new ArrayList<>();

}
