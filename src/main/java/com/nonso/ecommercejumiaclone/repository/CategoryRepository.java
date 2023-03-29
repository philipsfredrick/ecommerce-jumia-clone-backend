package com.nonso.ecommercejumiaclone.repository;

import com.nonso.ecommercejumiaclone.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findById(Long categoryId);
    Category findByCategoryName(String categoryName);
}
