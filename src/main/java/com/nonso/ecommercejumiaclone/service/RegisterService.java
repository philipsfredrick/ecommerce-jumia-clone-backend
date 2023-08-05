package com.nonso.ecommercejumiaclone.service;


import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.exception.JumiaCloneException;
import com.nonso.ecommercejumiaclone.dto.request.UserSignUpRequest;
import com.nonso.ecommercejumiaclone.dto.request.VendorSignUpRequest;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
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
public class RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public void createUser(UserSignUpRequest request, MultipartFile file) {
        try {
            String fileUrl = cloudinaryService.uploadImage(file);
            User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .avatarUrl(fileUrl)
                    .role(UserRole.USER)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            userRepository.save(user);
        } catch (Exception e) {
            log.error(format("An error occurred while creating user account, please contact support. " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new JumiaCloneException("An error occurred while creating user account, please contact support.",
                    INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void createVendor(VendorSignUpRequest request, MultipartFile file) {
        try {
            String fileUrl = cloudinaryService.uploadImage(file);
            User vendor = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .avatarUrl(fileUrl)
                    .role(UserRole.VENDOR)
                    .password(passwordEncoder.encode(request.getPassword()))
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
