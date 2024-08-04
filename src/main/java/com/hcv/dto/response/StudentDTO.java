package com.hcv.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StudentDTO extends BaseDTO {

    private String maSo;
    private String name;
    private String myClass;
    private String email;
    private String phoneNumber;
    private SubjectDTO subjects;
    private List<PointResponse> points = new ArrayList<>();

}
