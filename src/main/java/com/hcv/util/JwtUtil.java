package com.hcv.util;

import com.hcv.entity.UserEntity;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${com.hcv.jwt.secret-key}")
    private String secretKey;

    public String generateToken(UserEntity userEntity) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userEntity.getUsername())
                .issuer("qlda-hcv.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(userEntity))
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

    private String buildScope(UserEntity userEntity) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(userEntity.getRoles())) {
            userEntity.getRoles().forEach(roleEntity -> stringJoiner.add(roleEntity.getCode().name()));
        }

        return stringJoiner.toString();
    }

    public boolean introspectToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return isTokenValid(signedJWT) && !isTokenExpired(signedJWT);
    }

    public boolean isTokenValid(SignedJWT signedJWT) throws JOSEException {
        JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());
        return signedJWT.verify(jwsVerifier);
    }

    public boolean isTokenExpired(SignedJWT signedJWT) throws ParseException {
        Date expirationTimeToken = signedJWT.getJWTClaimsSet().getExpirationTime();
        return expirationTimeToken.before(new Date());
    }

}