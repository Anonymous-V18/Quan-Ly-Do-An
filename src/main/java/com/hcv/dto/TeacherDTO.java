package com.hcv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TeacherDTO extends BaseDTO<TeacherDTO> {

    private String maSo;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date DOB;
    private String hocVi;
    private String email;
    private String phoneNumber;
    private String chucVu;
    private SubjectDTO subjects;

}
