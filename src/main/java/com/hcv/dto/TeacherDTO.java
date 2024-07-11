package com.hcv.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDTO extends BaseDTO {

    private String maSo;
    private String name;
    private String hocVi;
    private String email;
    private String phoneNumber;
    private String chucVu;
    private SubjectDTO subjects;

}
