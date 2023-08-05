package com.nonso.ecommercejumiaclone.dto.response;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResource implements Serializable {

    @Serial
    private static final long serialVersionUID = -3610419978750672391L;

    private Long id;
    private String productName;
    private String imageUrl;
    private BigDecimal price;
    private Integer quantity;

}
