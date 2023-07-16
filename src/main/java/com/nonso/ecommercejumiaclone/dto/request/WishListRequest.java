package com.nonso.ecommercejumiaclone.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishListRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -4489142363933070669L;

    @NotBlank(message = " product id cannot be blank")
    private Long productId;

    @NotNull
    private Long userId;
}
