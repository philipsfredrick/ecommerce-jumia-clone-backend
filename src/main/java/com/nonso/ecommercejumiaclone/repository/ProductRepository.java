package com.nonso.ecommercejumiaclone.repository;

import com.nonso.ecommercejumiaclone.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndCategoryId(Long productId, Long categoryId);
    Optional<Product> findById(Long productId);

    Page<Product> findProductsByProductNameOrProductPrice(String productName, BigDecimal productPrice, Pageable pageable);

}
