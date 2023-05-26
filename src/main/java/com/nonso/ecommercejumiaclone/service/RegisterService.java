package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.payload.request.UserSignUpRequest;
import com.nonso.ecommercejumiaclone.payload.request.VendorSignUpRequest;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService {

    void createUser(UserSignUpRequest request, MultipartFile file);

    void createVendor(VendorSignUpRequest request, MultipartFile file);



}
