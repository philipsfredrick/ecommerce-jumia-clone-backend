package com.nonso.ecommercejumiaclone.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8461801304169303811L;

    @NotBlank(message = "product name cannot be blank")
    @JsonProperty("product_name")
    private String productName;

    @NotBlank(message = "please add a description for product")
    private String description;

    @NotBlank(message = "please upload product image")
    private String imageUrl;
    @NotBlank(message = "product quantity cannot be blank")
    private Long quantity;
    @NotBlank(message = "product price cannot be blank")
    private BigDecimal productPrice;
    @NotBlank(message = "category of product cannot be blank")
    private Long categoryId;

}
