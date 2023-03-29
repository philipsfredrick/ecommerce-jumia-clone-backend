package com.nonso.ecommercejumiaclone.service.impl;

import com.nonso.ecommercejumiaclone.config.security.JwtService;
import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.payload.request.LoginRequest;
import com.nonso.ecommercejumiaclone.payload.response.AuthResponse;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
import com.nonso.ecommercejumiaclone.service.LoginService;
import com.nonso.ecommercejumiaclone.utils.CloudinaryService;
import jakarta.mail.AuthenticationFailedException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;


    private final JwtService jwtService;
    /**
     * @param loginRequest
     * @return
     * @throws Exception
     */
    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) throws Exception {

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
        ));

        if (!auth.isAuthenticated() ) {
            throw new AuthenticationFailedException("Wrong email or password");
        }
        User user = userRepository.findUserByEmailAndRole(loginRequest.getEmail(), UserRole.USER);
        if (user == null){
            throw new UsernameNotFoundException("User with email" + loginRequest.getEmail() + " not found");
        }
        if(!user.getRole().equals(UserRole.USER)){
            throw new AuthenticationFailedException("Unauthorised");
        }

        String userToken = jwtService.generateToken(user);

        return new AuthResponse(userToken, user.getName(), user.getAvatarUrl());
    }

    @Override
    public AuthResponse loginVendor(LoginRequest loginRequest) throws Exception {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
        ));

        if (!auth.isAuthenticated() ) {
            throw new AuthenticationFailedException("Wrong email or password");
        }
        User vendor = userRepository.findUserByEmailAndRole(loginRequest.getEmail(), UserRole.VENDOR);
        if (vendor == null){
            throw new UsernameNotFoundException("User with email" + loginRequest.getEmail() + " not found");
        }
        if(!vendor.getRole().equals(UserRole.VENDOR)){
            throw new AuthenticationFailedException("Unauthorised");
        }

        String vendorToken = jwtService.generateToken(vendor);

        return new AuthResponse(vendorToken, vendor.getName(), vendor.getAvatarUrl());
    }
}
