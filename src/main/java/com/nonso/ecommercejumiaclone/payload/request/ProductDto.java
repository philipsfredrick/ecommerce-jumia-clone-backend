package com.nonso.ecommercejumiaclone.payload.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productName;
    private String logo;
    private Long quantity;
    private BigDecimal price;
    private Long categoryId;

}
