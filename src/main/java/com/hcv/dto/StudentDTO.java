package com.hcv.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StudentDTO extends BaseDTO<StudentDTO> {

    private String maSo;
    private String name;
    private Date DOB;
    private String myClass;
    private String email;
    private String phoneNumber;

}
