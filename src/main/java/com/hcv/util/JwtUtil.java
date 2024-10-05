package com.hcv.util;

import com.hcv.entity.User;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IInvalidatedTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JwtUtil {

    IInvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${com.hcv.jwt.secret-key}")
    String secretKey;

    @NonFinal
    @Value("${com.hcv.jwt.valid-duration}")
    long validDuration;

    @NonFinal
    @Value("${com.hcv.jwt.refreshable-duration}")
    long refreshableDuration;

    public String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(getTeacherOrStudentID(user))
                .issuer("qlklks-hcv.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(validDuration, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .claim("username", user.getUsername())
                .claim("expirationTime_Refresh", new Date(Instant.now().plus(refreshableDuration, ChronoUnit.SECONDS).toEpochMilli()))
                .build();

        Payload payload = jwtClaimsSet.toPayload();

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTeacherOrStudentID(User user) {
        List<String> roleName = user.getRoles().stream()
                .map(roleEntity -> roleEntity.getCode().name())
                .toList();
        String id;

        if (roleName.contains("STUDENT")) {
            id = user.getStudent().getId();
        } else {
            id = user.getTeacher().getId();
        }

        if (id == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        return id;
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(roleEntity -> stringJoiner.add(roleEntity.getCode().name()));
        }

        return stringJoiner.toString();
    }

    public SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());
        boolean isTokenValid = signedJWT.verify(jwsVerifier);

        if (!isTokenValid) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }

        Date expirationTimeToken = isRefresh
                ? new Date(Instant.now().plus(validDuration, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean isTokenExpired = expirationTimeToken.before(new Date());

        if (isTokenExpired) {
            throw new AppException(ErrorCode.EXPIRATION_TOKEN);
        }

        boolean isTokenExistedInBlackList = invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID());
        if (isTokenExistedInBlackList) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }


}