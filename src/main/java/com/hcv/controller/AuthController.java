package com.hcv.controller;

import com.hcv.dto.request.LogInInput;
import com.hcv.dto.request.LogoutInput;
import com.hcv.dto.request.RefreshInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.LoginOutput;
import com.hcv.dto.response.RefreshOutput;
import com.hcv.service.IAuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthController {

    IAuthService authService;

    @PostMapping("/log-in")
    public ApiResponse<LoginOutput> createAuthenticationToken(@RequestBody @Valid LogInInput request) {
        String accessToken = authService.authentication(request.getUsername(), request.getPassword());
        return ApiResponse.<LoginOutput>builder()
                .result(LoginOutput.builder()
                        .accessToken(accessToken)
                        .build())
                .build();
    }

    @PostMapping("/log-out")
    public ApiResponse<String> logout(@RequestBody LogoutInput request) throws ParseException, JOSEException {
        authService.logout(request.getToken());
        return ApiResponse.<String>builder()
                .message("Đăng xuất thành công !")
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<RefreshOutput> refresh(@RequestBody RefreshInput request) throws ParseException, JOSEException {
        RefreshOutput response = authService.refreshToken(request.getToken());
        return ApiResponse.<RefreshOutput>builder()
                .result(response)
                .build();
    }

}
