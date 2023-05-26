package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.entities.WishList;
import com.nonso.ecommercejumiaclone.payload.response.WishListResource;

import java.util.List;

public interface WishListService {

   WishList createWishList();

   List<WishListResource> getAllWishListItems(Long userId);

   WishListResource addToWishList(Long productId);

   WishListResource removeFromWishList(Long wishListId, Long productId);

   List<WishList> getAllWishlists();

   List<WishList> getWishlistsForUser(Long userId);

   void deleteWishlist(Long wishListId);
}
