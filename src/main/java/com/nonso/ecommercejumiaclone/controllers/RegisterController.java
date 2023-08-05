package com.nonso.ecommercejumiaclone.controllers;

import com.google.gson.Gson;
import com.nonso.ecommercejumiaclone.dto.request.UserSignUpRequest;
import com.nonso.ecommercejumiaclone.dto.request.VendorSignUpRequest;
import com.nonso.ecommercejumiaclone.dto.response.RegistrationResponse;
import com.nonso.ecommercejumiaclone.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/user/signup")
    public ResponseEntity<?> registerUser(@RequestParam("register") String register, @RequestParam("file") MultipartFile file) {
        UserSignUpRequest request = new Gson().fromJson(register, UserSignUpRequest.class);
        registerService.createUser(request, file);
        return new ResponseEntity<>(new RegistrationResponse("User account created successfully"), CREATED);
    }

    @PostMapping("/vendor/signup")
    public ResponseEntity<?> registerVendor(@RequestParam("register") String register, @RequestParam("file") MultipartFile file) {
       VendorSignUpRequest vendorRequest = new Gson().fromJson(register, VendorSignUpRequest.class);
        registerService.createVendor(vendorRequest, file);
        return new ResponseEntity<>(new RegistrationResponse("Vendor account created successfully"), CREATED);
    }
}
