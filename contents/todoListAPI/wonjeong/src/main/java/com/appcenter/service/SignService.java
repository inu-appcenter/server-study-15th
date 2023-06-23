package com.appcenter.service;

import com.appcenter.data.dto.result.SignInResultDTO;
import com.appcenter.data.dto.result.SignUpResultDTO;

public interface SignService {

    SignUpResultDTO signUp(String id, String password, String name, String role);

    SignInResultDTO signIn(String id, String password) throws RuntimeException;

}
