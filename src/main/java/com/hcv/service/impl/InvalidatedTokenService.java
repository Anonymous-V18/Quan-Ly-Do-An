package com.hcv.service.impl;

import com.hcv.entity.InvalidatedToken;
import com.hcv.repository.IInvalidatedTokenRepository;
import com.hcv.service.IInvalidatedTokenService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvalidatedTokenService implements IInvalidatedTokenService {

    IInvalidatedTokenRepository invalidatedTokenRepository;

    @Override
    public void insert(SignedJWT signedJWT) throws ParseException, JOSEException {
        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jwtId)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

}
