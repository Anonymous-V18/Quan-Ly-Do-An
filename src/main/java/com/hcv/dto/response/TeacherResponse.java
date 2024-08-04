package com.hcv.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherResponse extends BaseDTO {
    private String maSo;
    private String name;
    private String hocVi;
    private String email;
    private String phoneNumber;
    private String chucVu;
}
