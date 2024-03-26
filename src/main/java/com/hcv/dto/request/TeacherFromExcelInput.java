package com.hcv.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TeacherFromExcelInput {

    private List<TeacherInput> teachers = new ArrayList<>();
}
