package com.nonso.ecommercejumiaclone.converter;

import com.nonso.ecommercejumiaclone.entities.Order;
import com.nonso.ecommercejumiaclone.dto.response.OrderResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class OrderToResourceConverter {

    private final CartItemToResourceConverter cartItemToResourceConverter;

    public OrderResource convert(Order order) {
        return OrderResource.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .orderTrackingNumber(order.getOrderTrackingNumber())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalAmount())
                .cartItemResources(order.getCartItems().parallelStream().map(cartItemToResourceConverter::convert).collect(toList()))
                .createdAt(order.getOrderDate())
                .build();
    }
}
