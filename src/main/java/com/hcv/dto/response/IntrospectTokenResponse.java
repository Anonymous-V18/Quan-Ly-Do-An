package com.hcv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IntrospectTokenResponse {
    private Boolean isAuthenticated;
}
