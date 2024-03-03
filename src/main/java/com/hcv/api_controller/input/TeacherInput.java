package com.hcv.api_controller.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;

@Getter
public class TeacherInput {

    private String maSo;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date DOB;
    private String hocVi;
    private String email;
    private String phoneNumber;
    private String chucVu;
    private Long user_id;
    private String departmentName;
    private String subjectName;

}
