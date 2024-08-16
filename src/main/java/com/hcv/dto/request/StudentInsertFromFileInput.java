package com.hcv.dto.request;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentInsertFromFileInput {

    @Valid
    List<StudentInput> students = new ArrayList<>();

}
