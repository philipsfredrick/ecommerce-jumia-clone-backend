package com.nonso.ecommercejumiaclone.repository;

import com.nonso.ecommercejumiaclone.entities.Cart;
import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUser(User user);

    Optional<Cart> findCartByUser_EmailAndUser_Role(String email, UserRole userRole);
    Optional<Cart> findByUserId(Long userId);

    List<Cart> findAllByUserOrderByCreatedAtDesc(User user);
}
