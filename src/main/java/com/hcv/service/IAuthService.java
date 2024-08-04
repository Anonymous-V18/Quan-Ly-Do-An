package com.hcv.service;

import com.hcv.dto.response.RefreshOutput;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthService {

    String authentication(String username, String password);

    boolean introspectToken(String token) throws ParseException, JOSEException;

    void logout(String token) throws ParseException, JOSEException;

    RefreshOutput refreshToken(String token) throws ParseException, JOSEException;

}
