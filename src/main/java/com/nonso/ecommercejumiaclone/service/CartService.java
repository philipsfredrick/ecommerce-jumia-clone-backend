package com.nonso.ecommercejumiaclone.service;

//import com.nonso.ecommercejumiaclone.security;
import com.nonso.ecommercejumiaclone.converter.CartItemToResourceConverter;
import com.nonso.ecommercejumiaclone.converter.CartToResourceConverter;
import com.nonso.ecommercejumiaclone.entities.Cart;
import com.nonso.ecommercejumiaclone.entities.CartItem;
import com.nonso.ecommercejumiaclone.entities.Product;
import com.nonso.ecommercejumiaclone.entities.User;
//import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.exception.CartServiceException;
import com.nonso.ecommercejumiaclone.exception.ProductServiceException;
//import com.nonso.ecommercejumiaclone.exception.UnAuthorizedException;
import com.nonso.ecommercejumiaclone.dto.response.CartItemResource;
import com.nonso.ecommercejumiaclone.dto.response.CartResource;
import com.nonso.ecommercejumiaclone.repository.CartItemRepository;
import com.nonso.ecommercejumiaclone.repository.CartRepository;
import com.nonso.ecommercejumiaclone.repository.ProductRepository;
//import com.nonso.ecommercejumiaclone.repository.UserRepository;
//import com.nonso.ecommercejumiaclone.service.CartService;
import com.nonso.ecommercejumiaclone.service.CredentialService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final CredentialService credentialService;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartToResourceConverter cartToResourceConverter;
    private final CartItemToResourceConverter cartItemToResourceConverter;

//    @Transactional
//    public Cart createCart() {
//        try {
//            String userEmail = Principal.getLoggedInUserDetails();
//            User user = userRepository.findUserByEmailAndRole(userEmail, UserRole.USER);
//            if (user.getRole().name().equals(UserRole.VENDOR.name())) {
//                throw new UnAuthorizedException("Unauthorized access for this resource");
//            }
//            Optional<Cart> cartOptional = cartRepository.findByUserId(user.getId());
//            if (cartOptional.isPresent()) {
//                return cartOptional.get();
//            } else {
//                Cart cart = new Cart();
//                cart.setUser(user);
//                cart.setGrandTotal(BigDecimal.ZERO);
//                return cartRepository.save(cart);
//            }
//        } catch (Exception e) {
//            log.error(format("An error occurred while creating cart " +
//                    "Possible reasons: %s", e.getLocalizedMessage()));
//            throw new CartServiceException("Cart could not be created");
//        }
//    }


    @Transactional
    public CartResource addProductToCart(Long productId, HttpServletRequest httpServletRequest) {
        try {
            User user = credentialService.getUserAccount(httpServletRequest);
            credentialService.validateUser(user, List.of("USER"));
            Product product = productRepository.findById(productId).orElseThrow(
                    () -> new ProductServiceException("Product not found"));

            Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(
                    ()-> new CartServiceException("Cart not found for user "));
            if (cart == null) {
                cart = new Cart();
                cart.setUser(user);
            }

            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(1)
                    .unitPrice(product.getProductPrice())
                    .build();

            cart.getCartItems().add(cartItem);
            cart.calculateGrandTotal();

//            Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
//                    .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
//                    .findFirst();
//            if (cartItemOptional.isPresent()) {
//                CartItem cartItem = cartItemOptional.get();
//                cartItem.setQuantity(cartItem.getQuantity() + quantity);
//                BigDecimal amount = cartItem.getPrice().add(product.getProductPrice().multiply(BigDecimal.valueOf(quantity)));
//                cartItem.setPrice(amount);
//            } else {

//            }
            cartItemRepository.save(cartItem);
            cartRepository.save(cart);
            return cartToResourceConverter.convert(cart);
        } catch (Exception e) {
            log.error(format("An error occurred while adding items to cart " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new CartServiceException("item could not be added to cart.");
        }
    }

    @Transactional
    public CartResource updateCartItems(Long cartItemId, Long cartId, Integer quantity, HttpServletRequest httpServletRequest) {
        try {
            User user = credentialService.getUserAccount(httpServletRequest);
            credentialService.validateUser(user, List.of("USER"));
            CartItem cartItem = cartItemRepository.findByIdAndCartId(cartItemId, cartId);

            if (cartItem != null) {
                cartItem.setQuantity(quantity);
                cartItemRepository.save(cartItem);

                Cart cart = cartRepository.findByUserId(user.getCart().getId()).orElseThrow(
                        ()-> new CartServiceException("Cart not found"));
                if (cart != null) {
                    cart.calculateGrandTotal();
                    cart.setUser(user);
                    cartRepository.save(cart);
                }
            }

        } catch (Exception e) {
            log.error(format("An error occurred while updating items in cart " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new CartServiceException("item could not be updated.");
        }
        return null;
    }

    @Transactional(readOnly = true)
    public CartResource getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(
                ()-> new CartServiceException("Your cart not found. Please contact support"));
        return cartToResourceConverter.convert(cart);
    }

    @Transactional
    public CartResource removeItemFromCart(Long cartItemId, Long cartId, HttpServletRequest httpServletRequest) {
        try {
            //Validate that the user is authenticated and has the correct scope.
            User user = credentialService.getUserAccount(httpServletRequest);
            credentialService.validateUser(user, List.of("USER"));
            CartItem cartItem = cartItemRepository.findByIdAndCartId(cartItemId, cartId);

            if (cartItem != null) {
                Cart cart = cartItem.getCart();
                cart.getCartItems().remove(cartItem);
                cart.calculateGrandTotal();
                cartItemRepository.delete(cartItem);
                cartRepository.save(cart);
                return cartToResourceConverter.convert(cart);
            }
//            Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
//                    .filter(cartItem -> cartItem.getId().equals(cartItemId))
//                    .findFirst();
//            if (cartItemOptional.isPresent()) {
//                cart.setGrandTotal(cart.getGrandTotal().subtract(cartItemOptional.get().getPrice()));
//                cart.getCartItems().remove(cartItemOptional.get());
//                cartItemRepository.deleteById(cartItemOptional.get().getId());
//            }

        } catch (Exception e) {
            log.error(format("An error occurred while removing items from cart " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new CartServiceException("item could not be removed from cart.");
        }
        return null;
    }

    @Transactional
    public List<CartItemResource> getAllCartItems(HttpServletRequest httpServletRequest) {
        User user = credentialService.getUserAccount(httpServletRequest);
        credentialService.validateUser(user, List.of("USER"));
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(
                () -> new CartServiceException("Cart not found"));
        return cart.getCartItems().stream().map(cartItemToResourceConverter::convert).collect(toList());
    }
}
