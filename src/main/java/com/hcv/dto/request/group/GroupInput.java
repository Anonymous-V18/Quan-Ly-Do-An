package com.hcv.dto.request.group;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GroupInput {

    private String researchId;
    private List<String> studentId = new ArrayList<>();

}
