package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.exception.JumiaCloneException;
import com.nonso.ecommercejumiaclone.exception.UnAuthorizedException;
import com.nonso.ecommercejumiaclone.security.JwtService;
import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class CredentialService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public User getUserAccount(HttpServletRequest httpServletRequest) {
        try {
            String token = jwtService.retrieveJWT(httpServletRequest).orElseThrow(
                    ()-> new JumiaCloneException("Token could not be decoded from request"));
            Long userId = jwtService.getUserIdFromToken(token);
            return userRepository.findById(userId).orElseThrow(
                    ()-> new UsernameNotFoundException("User not found"));
        } catch (Exception e) {
            log.error(format("An error occurred retrieving user from the jwt token. " +
                    " Possible reasons: %s", e.getLocalizedMessage()));
            throw new UsernameNotFoundException("User origins not found");
        }
    }

    public void validateUser(User user, List<String> authority) {
        List<String> grantedAuthorities = new ArrayList<>(user.getAuthorities().parallelStream().map(
                GrantedAuthority::getAuthority).map(String::toLowerCase).toList());

        authority = authority.parallelStream().map(String::toLowerCase).toList();

        if (!grantedAuthorities.removeAll(authority)) {
          throw new UnAuthorizedException(" User does not have the required authority. ");
        }
    }
}
