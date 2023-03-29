package com.nonso.ecommercejumiaclone.controllers;

import com.nonso.ecommercejumiaclone.payload.request.WishListDto;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class WishListController {

    private final WishListService wishListService;

    // save product as a wishlist item
    @PostMapping("/wishlists")
    public ResponseEntity<ApiResponse> addWishList(@RequestBody WishListDto wishListDto) {
        return new ResponseEntity<>(wishListService.createWishList(wishListDto), HttpStatus.CREATED);
    }

    // get all wishlist items for a user
}
