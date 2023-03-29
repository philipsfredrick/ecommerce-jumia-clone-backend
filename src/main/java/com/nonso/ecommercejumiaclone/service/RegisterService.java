package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.payload.request.LoginRequest;
import com.nonso.ecommercejumiaclone.payload.request.UserSignUpRequest;
import com.nonso.ecommercejumiaclone.payload.request.VendorSignUpRequest;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.payload.response.AuthResponse;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService {

    ApiResponse createUser(UserSignUpRequest request, MultipartFile file) throws Exception;

    ApiResponse createVendor(VendorSignUpRequest request, MultipartFile file) throws Exception;



}
