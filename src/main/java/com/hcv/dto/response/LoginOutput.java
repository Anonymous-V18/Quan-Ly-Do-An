package com.hcv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LoginOutput {

    private String accessToken;
    private Long userId;
    private List<String> allRoles;
    private Object result;

}
