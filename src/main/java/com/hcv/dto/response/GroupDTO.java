package com.hcv.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GroupDTO extends BaseDTO {

    private ResearchDTO researches;
    private List<StudentDTO> students = new ArrayList<>();
}
