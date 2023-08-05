package com.nonso.ecommercejumiaclone.converter;

import com.nonso.ecommercejumiaclone.entities.CartItem;
import com.nonso.ecommercejumiaclone.dto.response.CartItemResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CartItemToResourceConverter {

    public CartItemResource convert(CartItem cartItem) {
        return CartItemResource.builder()
                .id(cartItem.getId())
                .productName(cartItem.getProduct().getProductName())
                .imageUrl(cartItem.getProduct().getImageUrl())
                .price(cartItem.getUnitPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
