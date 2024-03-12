package com.hcv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginOutput {

    private String accessToken;
    private String allRole;

}
