package com.nonso.ecommercejumiaclone.repository;

import com.nonso.ecommercejumiaclone.entities.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<WishList> findWishListByUserId(Long userId);

    List<WishList> findByUserId(Long userId);
    Optional<WishList> findById(Long wishListId);
}
