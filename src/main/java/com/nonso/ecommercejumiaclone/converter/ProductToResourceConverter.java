package com.nonso.ecommercejumiaclone.converter;

import com.nonso.ecommercejumiaclone.entities.Product;
import com.nonso.ecommercejumiaclone.payload.response.ProductResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductToResourceConverter {

    public ProductResource convert(Product product) {

        return ProductResource.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .imageUrl(product.getImageUrl())
                .quantity(product.getQuantity())
                .productPrice(product.getProductPrice())
                .categoryId(product.getCategory().getId())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
