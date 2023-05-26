package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.entities.Cart;
import com.nonso.ecommercejumiaclone.payload.response.CartItemResource;
import com.nonso.ecommercejumiaclone.payload.response.CartResource;

import java.util.List;


public interface CartService {

    Cart createCart();
    CartResource addItemToCart(Long productId, Integer quantity);

    CartResource getCartByUserId(Long userId);

    CartResource removeItemFromCart(Long cartId, Long cartItemId);

    List<CartItemResource> getAllCartItems(Long cartId);
}
