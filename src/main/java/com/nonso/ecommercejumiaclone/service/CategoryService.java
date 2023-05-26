package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.entities.Category;
import com.nonso.ecommercejumiaclone.payload.request.CategoryRequest;
import com.nonso.ecommercejumiaclone.payload.response.CategoryResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    CategoryResource createCategory(CategoryRequest categoryRequest, MultipartFile file);

    List<CategoryResource> getAllCategories();
    Category findCategoryByName(String categoryName);
    Category findCategoryById(Long categoryId);

    CategoryResource updateCategory(Long categoryId, CategoryRequest categoryRequest, MultipartFile file);

    void deleteCategoryById(Long categoryId);
}
