package com.nonso.ecommercejumiaclone.dto.response;

import com.nonso.ecommercejumiaclone.entities.enums.OrderStatus;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResource implements Serializable {

    @Serial
    private static final long serialVersionUID = -8645283278461190121L;

    private Long orderId;

    private Long userId;

    private String orderTrackingNumber;

    private OrderStatus orderStatus;

    private BigDecimal totalPrice;

    List<CartItemResource> cartItemResources;

    private LocalDateTime createdAt;
}
