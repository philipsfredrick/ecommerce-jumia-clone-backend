package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.security.JwtService;
import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.exception.UnAuthorizedException;
import com.nonso.ecommercejumiaclone.dto.request.LoginRequest;
import com.nonso.ecommercejumiaclone.dto.response.AuthenticationResponse;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nonso.ecommercejumiaclone.dto.ErrorCode.INVALID_CREDENTIALS;
import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class LoginService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findUserByEmailAndRole(loginRequest.getEmail(), UserRole.USER);

            var userToken = jwtService.generateJwtToken(user);

            return new AuthenticationResponse(userToken, user.getName(), user.getAvatarUrl(), user.getRole());
        } catch (Exception e) {
            log.error(format("An error occurred while authenticating login request, please contact support. " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new UnAuthorizedException("Invalid email/or password", INVALID_CREDENTIALS);
        }
    }

    @Transactional
    public AuthenticationResponse loginVendor(LoginRequest loginRequest) {
       try {
           Authentication authentication = authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           loginRequest.getEmail(), loginRequest.getPassword()
                   ));
           SecurityContextHolder.getContext().setAuthentication(authentication);

           User vendor = userRepository.findUserByEmailAndRole(loginRequest.getEmail(), UserRole.VENDOR);

           var vendorToken = jwtService.generateJwtToken(vendor);

           return new AuthenticationResponse(vendorToken, vendor.getName(), vendor.getAvatarUrl(), vendor.getRole());
       } catch (Exception e) {
           log.info(format("An error occurred while authenticating vendor login request, please contact support. " +
                   "Possible reasons: %s", e.getLocalizedMessage()));
           throw new UnAuthorizedException("Invalid email/or password", INVALID_CREDENTIALS);
       }
    }
}
