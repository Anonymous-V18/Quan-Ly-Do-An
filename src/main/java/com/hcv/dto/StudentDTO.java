package com.hcv.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO extends BaseDTO {

    private String maSo;
    private String name;
    private String myClass;
    private String email;
    private String phoneNumber;
    private SubjectDTO subjects;
    private UserDTO users;

}
