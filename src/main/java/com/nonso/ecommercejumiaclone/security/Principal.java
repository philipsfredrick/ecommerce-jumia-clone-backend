package com.nonso.ecommercejumiaclone.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class Principal {
    public static String getLoggedInUserDetails() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof org.springframework.security.core.userdetails.UserDetails)) {
            throw new Exception("user not found");
        }
        return ((org.springframework.security.core.userdetails.UserDetails)principal).getUsername();
    }
}
