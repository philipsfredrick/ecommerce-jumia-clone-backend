package com.nonso.ecommercejumiaclone.service.impl;



import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.payload.request.UserSignUpRequest;
import com.nonso.ecommercejumiaclone.payload.request.VendorSignUpRequest;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
import com.nonso.ecommercejumiaclone.service.RegisterService;
import com.nonso.ecommercejumiaclone.utils.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinary;

    /**
     *
     * @param request
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponse createUser(UserSignUpRequest request, MultipartFile file) throws Exception {

        User verifyUser = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User with email" + request.getEmail() + " not found"));
        if (verifyUser != null) {
            throw new Exception("User already registered");
        }
        String fileUrl = cloudinary.uploadImage(file);
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .gender(request.getGender())
                .avatarUrl(fileUrl)
                .role(UserRole.USER)
                .build();

        userRepository.save(user);

        ApiResponse response = new ApiResponse<>("User registered successfully", true, user);
        return response;
    }

    /**
     * @param request
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponse createVendor(VendorSignUpRequest request, MultipartFile file) throws Exception {

        User verifyUser = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User with email" + request.getEmail() + " not found"));
        if (verifyUser != null) {
            throw new Exception("User already registered");
        }
        String fileUrl = cloudinary.uploadImage(file);
        User vendor = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .gender(request.getGender())
                .avatarUrl(fileUrl)
                .role(UserRole.VENDOR)
                .build();

        userRepository.save(vendor);
        ApiResponse response = new ApiResponse<>("Vendor registered successfully", true, vendor);
        return response;
    }

}
