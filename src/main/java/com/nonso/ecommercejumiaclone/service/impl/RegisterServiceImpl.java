package com.nonso.ecommercejumiaclone.service.impl;


import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.exception.JumiaCloneException;
import com.nonso.ecommercejumiaclone.payload.request.UserSignUpRequest;
import com.nonso.ecommercejumiaclone.payload.request.VendorSignUpRequest;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
import com.nonso.ecommercejumiaclone.service.RegisterService;
import com.nonso.ecommercejumiaclone.utils.CloudinaryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Service
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(UserSignUpRequest request, MultipartFile file) {
        try {
            String fileUrl = cloudinaryService.uploadImage(file);
            User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .avatarUrl(fileUrl)
                    .role(UserRole.USER)
                    .build();
            userRepository.save(user);
        } catch (Exception e) {
            log.error(format("An error occurred while creating user account, please contact support. " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new JumiaCloneException("An error occurred while creating user account, please contact support.",
                    INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void createVendor(VendorSignUpRequest request, MultipartFile file) {
        try {
            String fileUrl = cloudinaryService.uploadImage(file);
            User vendor = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .avatarUrl(fileUrl)
                    .role(UserRole.VENDOR)
                    .build();
            userRepository.save(vendor);
        } catch (Exception e) {
            log.error(format("An error occurred while creating vendor account, please contact support. " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new JumiaCloneException("An error occurred while creating vendor account, please contact support.",
                    INTERNAL_SERVER_ERROR);
        }
    }
}
