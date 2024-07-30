package com.hcv.dto.request.Teacher;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TeacherFromExcelInput {

    @NotNull(message = "INVALID_TEACHER_INPUT_PARAM")
    @Size(min = 1, message = "INVALID_LIST_STUDENT")
    @Valid
    private List<TeacherInput> teachers = new ArrayList<>();
}
