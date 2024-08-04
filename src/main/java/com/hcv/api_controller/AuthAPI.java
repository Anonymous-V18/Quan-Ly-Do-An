package com.hcv.api_controller;

import com.hcv.dto.request.LogInInput;
import com.hcv.dto.request.LogoutInput;
import com.hcv.dto.request.RefreshInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.IntrospectTokenResponse;
import com.hcv.dto.response.LoginOutput;
import com.hcv.dto.response.RefreshOutput;
import com.hcv.service.IAuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthAPI {

    IAuthService authService;

    @PostMapping("/log-in")
    public ApiResponse<LoginOutput> createAuthenticationToken(@RequestBody @Valid LogInInput request) {
        String accessToken = authService.authentication(request.getUsername(), request.getPassword());
        LoginOutput response = new LoginOutput(accessToken);
        return ApiResponse.<LoginOutput>builder()
                .result(response)
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

    @PostMapping("/introspectToken")
    public ApiResponse<IntrospectTokenResponse> authentication(@RequestParam("Token") String token) throws ParseException, JOSEException {
        boolean isAuthenticated = authService.introspectToken(token);
        IntrospectTokenResponse response = new IntrospectTokenResponse(isAuthenticated);
        return ApiResponse.<IntrospectTokenResponse>builder()
                .result(response)
                .build();
    }

}
