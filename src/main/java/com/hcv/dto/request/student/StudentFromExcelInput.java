package com.hcv.dto.request.Student;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StudentFromExcelInput {

    @NotNull(message = "INVALID_LIST_TEACHER")
    @Size(min = 1, message = "INVALID_LIST_TEACHER")
    @Valid
    private List<StudentInput> students = new ArrayList<>();
}
