package com.nonso.ecommercejumiaclone.controllers;

import com.nonso.ecommercejumiaclone.entities.WishList;
import com.nonso.ecommercejumiaclone.dto.response.WishListResource;
import com.nonso.ecommercejumiaclone.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/wishlists")
public class WishListController {

    private final WishListService wishListService;


    // get all wishlist items for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<WishListResource>> getAllUserWishLists(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(wishListService.getAllWishListItems(userId), OK);
    }

    // get all wishlists
    @GetMapping
    public ResponseEntity<List<WishList>> getAllWishLists() {
        return new ResponseEntity<>(wishListService.getAllWishlists(), OK);
    }

    // save product as a wishlist item
    @PostMapping("/items")
    public ResponseEntity<WishListResource> addToWishList(@RequestParam Long productId) {
        return new ResponseEntity<>(wishListService.addToWishList(productId),OK);
    }

    // delete product from wishlist
   @DeleteMapping("/{wishlistId}/items/{productId}")
    public ResponseEntity<WishListResource> removeFromWishList(
            @PathVariable("wishlistId") Long wishlistId,
            @PathVariable("productId") Long productId) {
        return new ResponseEntity<>(wishListService.removeFromWishList(wishlistId, productId), OK);
   }

}
