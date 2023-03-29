package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.payload.request.LoginRequest;
import com.nonso.ecommercejumiaclone.payload.response.AuthResponse;

public interface LoginService {

    AuthResponse loginUser(LoginRequest loginRequest) throws Exception;

    AuthResponse loginVendor(LoginRequest loginRequest) throws Exception;
}
