package com.nonso.ecommercejumiaclone.service.impl;

import com.nonso.ecommercejumiaclone.config.security.Principal;
import com.nonso.ecommercejumiaclone.entities.Cart;
import com.nonso.ecommercejumiaclone.entities.Product;
import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.exceptions.ProductDoesNotExistException;
import com.nonso.ecommercejumiaclone.payload.request.AddToCartDto;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.repository.CartRepository;
import com.nonso.ecommercejumiaclone.repository.ProductRepository;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
import com.nonso.ecommercejumiaclone.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    /**
     * @param addToCartDto
     * @return
     */
    @Override
    public ApiResponse<Cart> addToCart(AddToCartDto addToCartDto) throws Exception {
        String userEmail = Principal.getLoggedInUserDetails();

        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User with email" + userEmail + " not found"));
        Product product = productRepository.findById(addToCartDto.getProductId()).orElseThrow(()-> new ProductDoesNotExistException("Product not found"));
        if (user.getRole().name().equalsIgnoreCase(UserRole.VENDOR.name())) {
            throw new Exception("Unauthorised");
        }
        Cart cart = Cart.builder()
                .product(product)
                .user(user)
                .quantity(addToCartDto.getQuantity())
                .updatedAt(LocalDateTime.now())
                .build();
        cartRepository.save(cart);
        return new ApiResponse<>("Product added to cart", true, cart);
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponse<List<Cart>> viewCart() throws Exception {
        String userEmail = Principal.getLoggedInUserDetails();

        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User with email" + userEmail + " not found"));
        if (user.getRole().name().equalsIgnoreCase(UserRole.VENDOR.name())) {
            throw new Exception("Unauthorised");
        }
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedAtDesc(user);
        return new ApiResponse<>("Cart retrieved", true, cartList);
    }
}
