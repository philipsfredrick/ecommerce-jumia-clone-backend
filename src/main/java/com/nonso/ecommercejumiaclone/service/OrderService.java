package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.security.Principal;
import com.nonso.ecommercejumiaclone.converter.OrderToResourceConverter;
import com.nonso.ecommercejumiaclone.entities.*;
import com.nonso.ecommercejumiaclone.entities.enums.OrderStatus;
import com.nonso.ecommercejumiaclone.entities.enums.TransactionStatus;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.exception.*;
import com.nonso.ecommercejumiaclone.dto.request.OrderRequest;
import com.nonso.ecommercejumiaclone.dto.response.OrderResource;
import com.nonso.ecommercejumiaclone.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;

import java.util.List;

import java.util.UUID;

import static java.lang.String.format;
import static java.math.BigDecimal.valueOf;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final TransactionRepository transactionRepository;
    private final OrderToResourceConverter orderToResourceConverter;

    private Order createOrder(User user) {
        try {
            Order order = new Order();
            order.setUser(user);
            order.setOrderTrackingNumber(generateOrderTrackingNumber());
            order.setTotalAmount(BigDecimal.ZERO);
            order.setOrderStatus(OrderStatus.PROCESSING);
            return orderRepository.save(order);
        } catch (Exception e) {
            log.info(format("An error occurred while creating your order. Please contact support. " +
                    " Possible reasons: %s", e.getLocalizedMessage()));
            throw new OrderServiceException("Order could not be created for user. Does not have access. Please contact support");
        }
    }

    @Transactional
    public OrderResource placeOrder() {
        try {
            String userEmail = Principal.getLoggedInUserDetails();
            User user = userRepository.findUserByEmailAndRole(userEmail, UserRole.USER);
            if (user.getRole().name().equals(UserRole.VENDOR.name())) {
                throw new UnAuthorizedException("Vendor cannot create an order");
            }

            Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(
                    ()-> new CartServiceException("User's cart could not be found"));

            List<CartItem> cartItems = cartItemRepository.findCartItemsByCart_IdAndOrderIsNull(cart.getId());

            if(cartItems.size() == 0) {
                throw new CartServiceException("No item found in user's cart to place order");
            }

            Order order = createOrder(user);
            cartItems.parallelStream().forEach(cartItem -> cartItem.setOrder(order));

            final BigDecimal[] totalAmount = {BigDecimal.ZERO};

            cartItems.forEach(cartItem -> {
                    totalAmount[0] = totalAmount[0].add(cartItem.getUnitPrice());
                }
            );

            List<Transaction> transactionRecords = cartItems.parallelStream().map(
                    this::createTransactionRecord).collect(toList());
            transactionRepository.saveAll(transactionRecords);

            order.setCartItems(cartItems);
            order.setTotalAmount(totalAmount[0]);
            order.setOrderStatus(OrderStatus.PLACED);
            cartItemRepository.saveAll(cartItems);
            orderRepository.save(order);
            return orderToResourceConverter.convert(order);
        }catch (Exception e) {
            log.error(format("An error occurred while placing order. Please contact support." +
                    " Possible reasons: %s", e.getLocalizedMessage()));
            throw new UnAuthorizedException("Error occurred");
        }
    }

    private Transaction createTransactionRecord(CartItem cartItem) {
        return Transaction.builder()
                .amount(cartItem.getUnitPrice().multiply(valueOf(cartItem.getQuantity())))
                .product(cartItem.getProduct())
                .quantity((long) cartItem.getQuantity())
                .vendor(cartItem.getProduct().getVendor())
                .status(TransactionStatus.SUCCESSFUL)
                .build();
    }

    public OrderResource updateOrder(OrderRequest orderRequest) {
        return null;
    }

    public void cancelOrder() {

    }

    public List<OrderResource> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(orderToResourceConverter::convert).collect(toList());
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID (UUID version-4)
        return UUID.randomUUID().toString();
    }
}
