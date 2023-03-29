package com.nonso.ecommercejumiaclone.repository;

import com.nonso.ecommercejumiaclone.entities.Product;
import com.nonso.ecommercejumiaclone.payload.request.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndCategoryId(Long productId, Long categoryId);
    Optional<Product> findById(Long productId);

    @Query(value = "SELECT * FROM Product p WHERE p.productName LIKE '%?1%' AND p.productPrice LIKE '%?2%'", nativeQuery = true)
    Page<Product> listProducts(@Param("productName") String productName, @Param("productPrice") BigDecimal productPrice, Pageable pageable);

//    @Query(value = "SELECT ProductName, ProductPrice FROM Product", nativeQuery = true)
//    Page<Product> listProducts(@Param("productName") String productName, @Param("productPrice") BigDecimal productPrice, Pageable pageable);

}
