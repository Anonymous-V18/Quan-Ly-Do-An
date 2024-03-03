package com.hcv.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TeacherDTO extends BaseDTO<TeacherDTO> {

    private String maSo;
    private String name;
    private Date DOB;
    private String hocVi;
    private String email;
    private String phoneNumber;
    private String chucVu;


}
