package com.nonso.ecommercejumiaclone.dto.request;

import com.nonso.ecommercejumiaclone.entities.CartItem;
import com.nonso.ecommercejumiaclone.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5739459206578949921L;

    private Long orderId;
    private String orderTrackingNumber;
    private BigDecimal totalAmount;
    private List<CartItem> cartItems;
    private OrderStatus orderStatus;

}
