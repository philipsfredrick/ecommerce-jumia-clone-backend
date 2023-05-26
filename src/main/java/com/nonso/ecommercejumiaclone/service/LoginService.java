package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.payload.request.LoginRequest;
import com.nonso.ecommercejumiaclone.payload.response.AuthenticationResponse;

public interface LoginService {

    AuthenticationResponse loginUser(LoginRequest loginRequest);

    AuthenticationResponse loginVendor(LoginRequest loginRequest);
}
