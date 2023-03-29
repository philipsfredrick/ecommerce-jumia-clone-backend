package com.nonso.ecommercejumiaclone.payload.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
//    private String tokenType = "Bearer ";
    private String name;
    private String avatarUrl;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
