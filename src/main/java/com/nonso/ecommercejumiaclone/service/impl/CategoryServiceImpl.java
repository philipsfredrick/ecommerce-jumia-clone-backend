package com.nonso.ecommercejumiaclone.service.impl;

import com.nonso.ecommercejumiaclone.entities.Category;
import com.nonso.ecommercejumiaclone.exceptions.CustomNotFoundException;
import com.nonso.ecommercejumiaclone.payload.request.CategoryDto;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.repository.CategoryRepository;
import com.nonso.ecommercejumiaclone.service.CategoryService;
import com.nonso.ecommercejumiaclone.utils.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;


    /**
     * @param categoryDto
     * @return
     */
    @Override
    public ApiResponse<Category> createCategory(CategoryDto categoryDto, MultipartFile file) throws IOException {
        Category category = categoryRepository.findByCategoryName(categoryDto.getCategoryName());
        if (category != null) {
            return new ApiResponse<>("Category already exists", false);
        }
        String imageFile = cloudinaryService.uploadImage(file);
        Category newCategory = Category.builder()
                .categoryName(categoryDto.getCategoryName())
                .description(categoryDto.getDescription())
                .imageUrl(imageFile)
                .build();
        categoryRepository.save(newCategory);
        return new ApiResponse<>("Category created", true, newCategory);
    }


    /**
     *
     * @return
     */
    @Override
    public List<Category> getAllCategories() {
       return categoryRepository.findAll();
    }

    /**
     *
     * @param categoryName
     * @return
     */
    @Override
    public Category findCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    /**
     *
     * @param categoryId
     * @return
     */
    @Override
    public Optional<Category> findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    /**
     * @param categoryId
     * @param categoryDto
     * @return
     */
    @Override
    public ApiResponse updateCategory(Long categoryId, CategoryDto categoryDto, MultipartFile file) throws IOException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CustomNotFoundException("Category not found"));

        String imageFile = cloudinaryService.uploadImage(file);
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        category.setImageUrl(imageFile);
        categoryRepository.save(category);
        return new ApiResponse<>("Update successful", true, category);
    }

    /**
     *
     * @param categoryId
     */
    @Override
    public void deleteCategoryById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
