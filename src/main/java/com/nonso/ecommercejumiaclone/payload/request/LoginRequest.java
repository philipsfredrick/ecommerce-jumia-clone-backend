package com.nonso.ecommercejumiaclone.payload.request;

import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {
    private String email;
    private String password;
}
