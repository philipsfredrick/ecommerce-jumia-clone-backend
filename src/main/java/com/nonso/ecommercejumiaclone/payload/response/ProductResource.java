package com.nonso.ecommercejumiaclone.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResource implements Serializable {

    @Serial
    private static final long serialVersionUID = -558977560064930611L;

    private Long productId;
    private String productName;
    private String imageUrl;
    private Long quantity;
    private BigDecimal productPrice;
    private Long categoryId;
    @NotNull(message = "created_at must not be blank")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
