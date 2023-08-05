package com.nonso.ecommercejumiaclone.controllers;


import com.nonso.ecommercejumiaclone.dto.response.CartItemResource;
import com.nonso.ecommercejumiaclone.dto.response.CartResource;
import com.nonso.ecommercejumiaclone.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<CartResource> addItemToCart(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "productId") Long productId) {
       return new ResponseEntity<>(cartService.addProductToCart(productId, httpServletRequest),OK);
    }

    @PutMapping("/items")
    public ResponseEntity<CartResource> updateCartItems(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "cartItemId") Long cartItemId, @RequestParam(value = "cartId") Long cartId,
            @RequestParam(value = "quantity", defaultValue = "1", required = false) Integer quantity) {
        return new ResponseEntity<>(cartService.updateCartItems(cartItemId, cartId, quantity, httpServletRequest),OK);
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<CartResource> removeItemFromCart(
            HttpServletRequest httpServletRequest, @PathVariable("cartId") Long cartId,
            @PathVariable("cartItemId") Long cartItemId) {
        return new ResponseEntity<>(cartService.removeItemFromCart(cartItemId, cartId, httpServletRequest), OK);
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemResource>> getAllCartItems(HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(cartService.getAllCartItems(httpServletRequest), OK);
    }
}
