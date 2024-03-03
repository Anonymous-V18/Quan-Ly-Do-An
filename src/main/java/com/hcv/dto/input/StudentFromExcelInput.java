package com.hcv.dto.input;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StudentFromExcelInput {

    private List<StudentInput> students = new ArrayList<>();
}
