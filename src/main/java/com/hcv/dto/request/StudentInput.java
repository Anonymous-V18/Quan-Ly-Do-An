package com.hcv.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class StudentInput {

    private String maSo;
    private String name;
    private String myClass;
    private String email;
    private String phoneNumber;
    private String chucVu;
    @Setter
    private Long user_id;
    private String departmentName;
    private String subjectName;

}
