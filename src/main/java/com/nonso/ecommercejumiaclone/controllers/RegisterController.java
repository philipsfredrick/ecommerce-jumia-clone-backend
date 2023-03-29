package com.nonso.ecommercejumiaclone.controllers;

import com.google.gson.Gson;
import com.nonso.ecommercejumiaclone.payload.request.UserSignUpRequest;
import com.nonso.ecommercejumiaclone.payload.request.VendorSignUpRequest;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestParam("register") String register, @RequestParam("file") MultipartFile file) throws Exception {
        UserSignUpRequest request = new Gson().fromJson(register, UserSignUpRequest.class);

        return new ResponseEntity<>(registerService.createUser(request, file), HttpStatus.OK);
    }

    @PostMapping("/vendor/signup")
    public ResponseEntity<ApiResponse> registerVendor(@RequestParam("register") String register, @RequestParam("file") MultipartFile file) throws Exception {
       VendorSignUpRequest vendorRequest = new Gson().fromJson(register, VendorSignUpRequest.class);

        return new ResponseEntity<>(registerService.createVendor(vendorRequest, file), HttpStatus.OK);
    }
}
