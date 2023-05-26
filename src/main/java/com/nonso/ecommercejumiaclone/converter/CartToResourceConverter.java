package com.nonso.ecommercejumiaclone.converter;

import com.nonso.ecommercejumiaclone.entities.Cart;
import com.nonso.ecommercejumiaclone.payload.response.CartResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class CartToResourceConverter {

    private final CartItemToResourceConverter  cartItemToResourceConverter;

    public CartResource convert(Cart cart) {
        return CartResource.builder()
                .cartId(cart.getId())
                .userId(cart.getUser().getId())
                .grandTotal(cart.getGrandTotal())
                .cartItemResources(cart.getCartItems().parallelStream().map(cartItemToResourceConverter::convert).collect(toList()))
                .createdAt(cart.getCreatedAt())
                .build();
    }
}
