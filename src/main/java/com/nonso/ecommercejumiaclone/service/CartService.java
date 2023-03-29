package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.entities.Cart;
import com.nonso.ecommercejumiaclone.payload.request.AddToCartDto;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;

import java.util.List;

public interface CartService {
    ApiResponse<Cart> addToCart(AddToCartDto addToCartDto) throws Exception;

    ApiResponse<List<Cart>> viewCart() throws Exception;
}
