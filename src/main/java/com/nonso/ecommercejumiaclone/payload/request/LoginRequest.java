package com.nonso.ecommercejumiaclone.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5561814177664046364L;

    @Email(message = "please enter a valid email")
    @NotBlank(message = "email must not be blank")
    private String email;

    @NotBlank(message = "password must not be blank")
    private String password;
}
