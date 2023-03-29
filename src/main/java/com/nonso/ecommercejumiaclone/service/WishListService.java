package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.entities.WishList;
import com.nonso.ecommercejumiaclone.payload.request.WishListDto;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;

public interface WishListService {

    ApiResponse<WishList> createWishList(WishListDto wishListDto);
}
