package com.nonso.ecommercejumiaclone.repository;

import com.nonso.ecommercejumiaclone.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findCartItemsByCart_IdAndOrderIsNull(Long cartId);

    CartItem findByIdAndCartId(Long cartItemId, Long cartId);
}
