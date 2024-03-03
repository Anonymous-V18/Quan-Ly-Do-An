package com.hcv.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginOutput {

    private String accessToken;
    private String allRole;

}
