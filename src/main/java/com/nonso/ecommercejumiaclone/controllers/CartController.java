package com.nonso.ecommercejumiaclone.controllers;

import com.nonso.ecommercejumiaclone.entities.Cart;
import com.nonso.ecommercejumiaclone.payload.request.AddToCartDto;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/carts")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto) throws Exception {
        return ResponseEntity.ok(cartService.addToCart(addToCartDto));
    }

    @GetMapping("/carts")
    public ResponseEntity<ApiResponse<List<Cart>>> viewCartProducts() throws Exception {
        return ResponseEntity.ok(cartService.viewCart());
    }

}
