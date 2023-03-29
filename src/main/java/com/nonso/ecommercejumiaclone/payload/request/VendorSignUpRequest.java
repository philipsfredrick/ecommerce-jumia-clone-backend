package com.nonso.ecommercejumiaclone.payload.request;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorSignUpRequest implements Serializable {

    private String name;
    private String email;
    private String password;
    private String gender;
    private String avatarUrl;
}
