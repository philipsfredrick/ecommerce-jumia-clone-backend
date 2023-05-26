package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.payload.request.OrderRequest;
import com.nonso.ecommercejumiaclone.payload.response.OrderResource;

import java.util.List;

public interface OrderService {
    OrderResource placeOrder();

    OrderResource updateOrder(OrderRequest orderRequest);

    void cancelOrder();
    List<OrderResource> getOrdersByUserId(Long userId);
}
