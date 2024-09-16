package com.hcv.service.impl;

import com.hcv.dto.response.RefreshOutput;
import com.hcv.entity.User;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IUserRepository;
import com.hcv.service.IAuthService;
import com.hcv.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthService implements IAuthService {

    InvalidatedTokenService invalidatedTokenService;
    IUserRepository userRepository;
    JwtUtil jwtUtil;

    @Override
    public String authentication(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordValid = passwordEncoder.matches(password, user.getPassword());
        if (!isPasswordValid) {
            throw new AppException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }
        return jwtUtil.generateToken(user);
    }

    @Override
    public boolean introspectToken(String token) throws ParseException, JOSEException {
        boolean isValid = true;

        try {
            jwtUtil.verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void logout(String token) throws ParseException, JOSEException {
        try {
            SignedJWT signedToken = jwtUtil.verifyToken(token, true);
            invalidatedTokenService.insert(signedToken);
        } catch (AppException e) {
            log.info("Token invalid !");
        }
    }

    @Override
    public RefreshOutput refreshToken(String token) throws ParseException, JOSEException {
        SignedJWT signedToken = jwtUtil.verifyToken(token, true);

        invalidatedTokenService.insert(signedToken);

        String username = signedToken.getJWTClaimsSet().getStringClaim("username");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        String accessToken = jwtUtil.generateToken(user);

        return RefreshOutput.builder()
                .accessToken(accessToken)
                .build();
    }
}
