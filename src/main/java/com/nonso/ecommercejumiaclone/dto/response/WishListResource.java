package com.nonso.ecommercejumiaclone.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishListResource implements Serializable {

    @Serial
    private static final long serialVersionUID = 1777842274131255960L;

    private Long wishListId;

    private Set<ProductResource> productResources;

    @NotNull(message = "created_at must not be blank")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
