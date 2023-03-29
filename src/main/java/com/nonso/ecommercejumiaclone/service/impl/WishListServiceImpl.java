package com.nonso.ecommercejumiaclone.service.impl;

import com.nonso.ecommercejumiaclone.entities.Product;
import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.WishList;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.exceptions.CustomNotFoundException;
import com.nonso.ecommercejumiaclone.payload.request.WishListDto;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.repository.ProductRepository;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
import com.nonso.ecommercejumiaclone.repository.WishListRepository;
import com.nonso.ecommercejumiaclone.service.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;

    /**
     * @param wishListDto
     * @return
     */
    @Override
    public ApiResponse<WishList> createWishList(WishListDto wishListDto) {
        Product product = productRepository.findById(wishListDto.getProductId()).orElseThrow(()-> new CustomNotFoundException("Product not found"));
        User user = userRepository.findById(wishListDto.getUserId()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        if (user.getRole().name().equalsIgnoreCase(UserRole.VENDOR.name())) {
            throw new RuntimeException("Vendor cannot add product to wish list");
        }

        if (product == null || user == null) {
            throw new RuntimeException("Product or User not found");
        }
        WishList wishList = WishList.builder()
                .product(product)
                .user(user)
                .build();

        wishListRepository.save(wishList);
        return new ApiResponse<>("wishlist created", true, wishList);
    }
}
