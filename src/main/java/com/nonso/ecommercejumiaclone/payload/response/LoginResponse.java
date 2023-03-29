package com.nonso.ecommercejumiaclone.payload.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse<T> {
    private String message;
    private boolean status;
    private T data;
//    private String token;
}
