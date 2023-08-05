package com.nonso.ecommercejumiaclone.controllers;

import com.nonso.ecommercejumiaclone.dto.response.OrderResource;
import com.nonso.ecommercejumiaclone.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResource> placeOrder() {
       return new ResponseEntity<>(orderService.placeOrder(), CREATED);

    }

    @GetMapping
    public ResponseEntity<List<OrderResource>> getOrdersByCustomerId(@RequestParam Long userId) {
        List<OrderResource> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }


}
