package com.nonso.ecommercejumiaclone.service.impl;

import com.nonso.ecommercejumiaclone.config.security.Principal;
import com.nonso.ecommercejumiaclone.converter.CartItemToResourceConverter;
import com.nonso.ecommercejumiaclone.converter.CartToResourceConverter;
import com.nonso.ecommercejumiaclone.entities.Cart;
import com.nonso.ecommercejumiaclone.entities.CartItem;
import com.nonso.ecommercejumiaclone.entities.Product;
import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.exception.CartServiceException;
import com.nonso.ecommercejumiaclone.exception.ProductServiceException;
import com.nonso.ecommercejumiaclone.exception.UnAuthorizedException;
import com.nonso.ecommercejumiaclone.dto.response.CartItemResource;
import com.nonso.ecommercejumiaclone.dto.response.CartResource;
import com.nonso.ecommercejumiaclone.repository.CartItemRepository;
import com.nonso.ecommercejumiaclone.repository.CartRepository;
import com.nonso.ecommercejumiaclone.repository.ProductRepository;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
import com.nonso.ecommercejumiaclone.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartToResourceConverter cartToResourceConverter;
    private final CartItemToResourceConverter cartItemToResourceConverter;

    @Transactional
    public Cart createCart() {
        try {
            String userEmail = Principal.getLoggedInUserDetails();
            User user = userRepository.findUserByEmailAndRole(userEmail, UserRole.USER);
            if (user.getRole().name().equals(UserRole.VENDOR.name())) {
                throw new UnAuthorizedException("Unauthorized access for this resource");
            }
            Optional<Cart> cartOptional = cartRepository.findByUserId(user.getId());
            if (cartOptional.isPresent()) {
                return cartOptional.get();
            } else {
                Cart cart = new Cart();
                cart.setUser(user);
                cart.setGrandTotal(BigDecimal.ZERO);
                return cartRepository.save(cart);
            }
        } catch (Exception e) {
            log.error(format("An error occurred while creating cart " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new CartServiceException("Cart could not be created");
        }
    }


    @Transactional
    public CartResource addItemToCart(Long productId, Integer quantity) {
        try {
            Cart cart = createCart();
            Product product = productRepository.findById(productId).orElseThrow(
                    () -> new ProductServiceException("Product not found"));

            Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                    .findFirst();
            if (cartItemOptional.isPresent()) {
                CartItem cartItem = cartItemOptional.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                BigDecimal amount = cartItem.getPrice().add(product.getProductPrice().multiply(BigDecimal.valueOf(quantity)));
                cartItem.setPrice(amount);
            } else {
                BigDecimal total = product.getProductPrice().multiply(BigDecimal.valueOf(quantity));
                CartItem cartItem = CartItem.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(quantity)
                        .price(total)
                        .build();
                cart.getCartItems().add(cartItem);
                cartItem.setCart(cart);
                cartItemRepository.save(cartItem);
            }
            cart.setGrandTotal(getCartGrandTotal(cart.getId()));
            cartRepository.save(cart);
            return cartToResourceConverter.convert(cart);
        } catch (Exception e) {
            log.error(format("An error occurred while adding items to cart " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new CartServiceException("item could not be added to cart.");
        }
    }


    @Transactional(readOnly = true)
    public CartResource getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(
                ()-> new CartServiceException("Your cart not found. Please contact support"));
        cart.setGrandTotal(getCartGrandTotal(cart.getId()));// @TODO: This line should come with the enity with the help of Hibernate. remove this line
        return cartToResourceConverter.convert(cart);
    }

    @Transactional
    public CartResource removeItemFromCart(Long cartId, Long cartItemId) {
        try {
            //Validate that the user is authenticated and has the correct scope.
            Cart cart = cartRepository.findById(cartId).orElseThrow(
                    () -> new CartServiceException("Cart not found"));
            Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getId().equals(cartItemId))
                    .findFirst();
            if (cartItemOptional.isPresent()) {
                cart.setGrandTotal(cart.getGrandTotal().subtract(cartItemOptional.get().getPrice()));
                cart.getCartItems().remove(cartItemOptional.get());
                cartItemRepository.deleteById(cartItemOptional.get().getId());
            }
            cartRepository.save(cart);
            return cartToResourceConverter.convert(cart);
        } catch (Exception e) {
            log.error(format("An error occurred while removing items from cart " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new CartServiceException("item could not be removed from cart.");
        }
    }

    @Transactional
    public List<CartItemResource> getAllCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new CartServiceException("Cart not found"));
        cart.setGrandTotal(getCartGrandTotal(cartId));
        return cart.getCartItems().stream().map(cartItemToResourceConverter::convert).collect(toList());
    }


    private BigDecimal getCartGrandTotal(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new CartServiceException("Cart not found"));
        List<CartItem> cartItems = cart.getCartItems();
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            total = total.add(cartItem.getPrice());
        }
        cart.setGrandTotal(total);
        cartRepository.save(cart);
        return cart.getGrandTotal();
    }
}
