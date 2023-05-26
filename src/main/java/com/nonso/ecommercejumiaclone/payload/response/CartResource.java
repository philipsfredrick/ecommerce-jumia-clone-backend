package com.nonso.ecommercejumiaclone.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResource implements Serializable {

    @Serial
    private static final long serialVersionUID = 5403211555230649637L;

    private Long cartId;
    private Long userId;
    private BigDecimal grandTotal;
    List<CartItemResource> cartItemResources;
    @NotNull(message = "created_at must not be blank")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
