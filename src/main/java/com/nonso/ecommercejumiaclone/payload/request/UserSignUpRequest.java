package com.nonso.ecommercejumiaclone.payload.request;

import com.nonso.ecommercejumiaclone.entities.enums.Gender;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest implements Serializable {
    private String name;
    private String email;
    private String password;
    private String gender;
    private String avatarUrl;
}
