package com.nonso.ecommercejumiaclone.controllers;


import com.nonso.ecommercejumiaclone.payload.response.CartItemResource;
import com.nonso.ecommercejumiaclone.payload.response.CartResource;
import com.nonso.ecommercejumiaclone.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartResource> getCartByUserId(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(cartService.getCartByUserId(userId), OK);
    }

    @PostMapping("/items")
    public ResponseEntity<CartResource> addItemToCart(@RequestParam Long productId,
                                                      @RequestParam(value = "quantity", required = false, defaultValue = "1")
                                                      Integer quantity) {
       return new ResponseEntity<>(cartService.addItemToCart(productId, quantity),OK);
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<CartResource> removeItemFromCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("cartItemId") Long cartItemId) {
        return new ResponseEntity<>(cartService.removeItemFromCart(cartId, cartItemId), OK);
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItemResource>> getAllCartItems(@PathVariable("cartId") Long cartId) {
        return new ResponseEntity<>(cartService.getAllCartItems(cartId), OK);
    }

}
