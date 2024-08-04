package com.hcv.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface IInvalidatedTokenService {

    void insert(SignedJWT signedJWT) throws ParseException, JOSEException;

}
