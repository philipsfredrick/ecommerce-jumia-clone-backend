package com.nonso.ecommercejumiaclone.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorSignUpRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 9002323887679353960L;

    @NotBlank(message = "name must not be blank")
    @JsonProperty("name")
    private String name;

    @Email(message = "please enter a valid email")
    @NotBlank(message = "email must not be blank")
    private String email;

    @Size(min = 6, message = "password length must not be less than 6 characters")
    @NotBlank(message = "password must not be blank")
    private String password;

    @NotBlank(message = "please upload your image file")
    private String avatarUrl;
}
