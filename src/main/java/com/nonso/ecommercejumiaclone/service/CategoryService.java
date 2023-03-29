package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.entities.Category;
import com.nonso.ecommercejumiaclone.payload.request.CategoryDto;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    ApiResponse<Category> createCategory(CategoryDto categoryDto, MultipartFile file) throws IOException;

    List<Category> getAllCategories();
    Category findCategoryByName(String categoryName);
    Optional<Category> findCategoryById(Long categoryId);

    ApiResponse updateCategory(Long categoryId, CategoryDto categoryDto, MultipartFile file) throws IOException;

    void deleteCategoryById(Long categoryId);
}
