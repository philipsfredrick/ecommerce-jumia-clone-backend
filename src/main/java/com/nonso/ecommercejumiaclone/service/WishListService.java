package com.nonso.ecommercejumiaclone.service.impl;

import com.nonso.ecommercejumiaclone.config.security.Principal;
import com.nonso.ecommercejumiaclone.converter.WishListToResourceConverter;
import com.nonso.ecommercejumiaclone.entities.*;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.exception.ProductServiceException;
import com.nonso.ecommercejumiaclone.exception.WishListServiceException;
import com.nonso.ecommercejumiaclone.dto.response.WishListResource;
import com.nonso.ecommercejumiaclone.repository.ProductRepository;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
import com.nonso.ecommercejumiaclone.repository.WishListRepository;
import com.nonso.ecommercejumiaclone.service.WishListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishListRepository wishListRepository;
    private final WishListToResourceConverter wishListToResourceConverter;

    @Override
    @Transactional
    public WishList createWishList() {
        try {
            String userEmail = Principal.getLoggedInUserDetails();
            User user = userRepository.findUserByEmailAndRole(userEmail, UserRole.USER);
            if (user.getRole().name().equalsIgnoreCase(UserRole.VENDOR.name())) {
                throw new RuntimeException("Vendor cannot add product to wish list");
            }

            Optional<WishList> wishListOptional = wishListRepository.findWishListByUserId(user.getId());
            if (wishListOptional.isPresent()) {
                return wishListOptional.get();
            } else {
                WishList wishList = new WishList();
                wishList.setUser(user);
                return wishListRepository.save(wishList);
            }
        } catch (Exception e) {
            log.info(format("An error occurred while creating your wishlist. Please contact support " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new WishListServiceException("An error with creating user wishlist. Please contact support");
        }
    }

    @Override
    @Transactional
    public WishListResource addToWishList(Long productId) {
        try {
            WishList wishList = createWishList();
            Product product = productRepository.findById(productId).orElseThrow(
                    () -> new ProductServiceException("Product not found"));

            wishList.getProducts().add(product);
            wishListRepository.save(wishList);
            return wishListToResourceConverter.convert(wishList);
        } catch (Exception e) {
            log.info(format("An error occurred while adding to your wishlist. Please contact support " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new WishListServiceException("An error with adding to user's wishlist. Please contact support");
        }
    }

    @Override
    @Transactional
    public WishListResource removeFromWishList(Long wishListId, Long productId) {
        try {
            WishList wishList = wishListRepository.findById(wishListId).orElseThrow(
                    () -> new WishListServiceException("Wishlist not found"));
            Product product = productRepository.findById(productId).orElseThrow(
                    () -> new ProductServiceException("Product not found"));
            wishList.getProducts().remove(product);
            wishListRepository.save(wishList);
            return wishListToResourceConverter.convert(wishList);
        } catch (Exception e) {
            log.info(format("An error occurred while removing item from your wishlist. Please contact support " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new WishListServiceException("An error with removing item from user's wishlist. Please contact support");
        }
    }

    @Override
    public List<WishListResource> getAllWishListItems(Long userId) {
        List<WishList> wishLists = wishListRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
        return wishLists.parallelStream().map(wishListToResourceConverter::convert).collect(toList());
    }
    @Override
    public List<WishList> getAllWishlists() {
        return wishListRepository.findAll();
    }

    @Override
    public List<WishList> getWishlistsForUser(Long userId) {
        return wishListRepository.findByUserId(userId);
    }

    @Override
    public void deleteWishlist(Long wishListId) {
        wishListRepository.deleteById(wishListId);
    }
}
