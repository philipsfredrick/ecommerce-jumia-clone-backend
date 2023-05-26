package com.nonso.ecommercejumiaclone.controllers;

import com.nonso.ecommercejumiaclone.payload.request.LoginRequest;
import com.nonso.ecommercejumiaclone.payload.response.AuthenticationResponse;
import com.nonso.ecommercejumiaclone.service.LoginService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/user/login")
    public ResponseEntity<AuthenticationResponse> loginUserEntity(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(loginService.loginUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/vendor/login")
    public ResponseEntity<AuthenticationResponse> loginVendorEntity(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(loginService.loginVendor(loginRequest), HttpStatus.OK);
    }
}
