package com.hcv.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class StudentInput {

    private String maSo;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date DOB;
    private String myClass;
    private String email;
    private String phoneNumber;
    private String chucVu;
    @Setter
    private Long user_id;
    private String departmentName;
    private String subjectName;

}
