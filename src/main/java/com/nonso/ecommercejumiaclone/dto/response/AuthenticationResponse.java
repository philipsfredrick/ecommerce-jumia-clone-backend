package com.nonso.ecommercejumiaclone.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

//@TODO: Convert all pojos to java 17 records.
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 2749436181809833674L;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("name")
    private String name;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @NotBlank(message = "role must not be blank")
    @JsonProperty("role")
    private UserRole role;
}
