package com.hcv.api_controller;

import com.hcv.dto.request.user.LogInInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.IntrospectTokenResponse;
import com.hcv.dto.response.LoginOutput;
import com.hcv.service.IAuthService;
import com.hcv.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthAPI {

    IAuthService authService;
    JwtUtil jwtUtil;

    @PostMapping("/log-in")
    public ApiResponse<LoginOutput> createAuthenticationToken(@RequestBody @Valid LogInInput authInput) {
        String accessToken = authService.authentication(authInput.getUsername(), authInput.getPassword());
        LoginOutput response = new LoginOutput(accessToken, accessToken);
        return ApiResponse.<LoginOutput>builder()
                .result(response)
                .build();
    }

    @PostMapping("/introspectToken")
    public ApiResponse<IntrospectTokenResponse> authentication(@RequestParam("Token") String token) throws ParseException, JOSEException {
        boolean isAuthenticated = jwtUtil.introspectToken(token);
        IntrospectTokenResponse response = new IntrospectTokenResponse(isAuthenticated);
        return ApiResponse.<IntrospectTokenResponse>builder()
                .result(response)
                .build();

    }

}
