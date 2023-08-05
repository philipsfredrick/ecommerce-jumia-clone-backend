package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.converter.CategoryToResourceConverter;
import com.nonso.ecommercejumiaclone.entities.Category;
import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.exception.CustomNotFoundException;
import com.nonso.ecommercejumiaclone.dto.request.CategoryRequest;
import com.nonso.ecommercejumiaclone.dto.response.CategoryResource;
import com.nonso.ecommercejumiaclone.repository.CategoryRepository;
import com.nonso.ecommercejumiaclone.utils.CloudinaryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {
    private final CloudinaryService cloudinaryService;
    private final CredentialService credentialService;
    private final CategoryRepository categoryRepository;
    private final CategoryToResourceConverter categoryToResourceConverter;

    @Transactional
    public CategoryResource createCategory(CategoryRequest categoryRequest, MultipartFile file, HttpServletRequest httpServletRequest) {
        try {
            User vendor = credentialService.getUserAccount(httpServletRequest);
            credentialService.validateUser(vendor, List.of("VENDOR"));
            String imageFile = cloudinaryService.uploadImage(file);
            Category newCategory = Category.builder()
                    .categoryName(categoryRequest.getCategoryName())
                    .imageUrl(imageFile)
                    .build();
            categoryRepository.save(newCategory);
            return categoryToResourceConverter.convert(newCategory);
        } catch (Exception e) {
            log.error(format("An error occurred while creating a new category for products." +
                    "Possible reason: %s", e.getLocalizedMessage()));
            throw new CustomNotFoundException("Error creating a new category for products");
        }
    }

    @Transactional(readOnly = true)
    public List<CategoryResource> getAllCategories() {
//        User vendor = credentialService.getUserAccount(httpServletRequest);
//        credentialService.validateUser(vendor, List.of("VENDOR, USER"));
       List<Category> categoryResources = categoryRepository.findAll();
       return categoryResources.parallelStream().map(categoryToResourceConverter::convert).collect(toList());
    }

    @Transactional
    public CategoryResource updateCategory(Long categoryId, CategoryRequest categoryRequest,
                                           MultipartFile file, HttpServletRequest httpServletRequest) {
        try {
            User vendor = credentialService.getUserAccount(httpServletRequest);
            credentialService.validateUser(vendor, List.of("VENDOR"));
            Category category = categoryRepository.findById(categoryId).orElseThrow(
                    () -> new CustomNotFoundException("Category not found"));

            String imageFile = cloudinaryService.uploadImage(file);
            category.setCategoryName(categoryRequest.getCategoryName());
            category.setImageUrl(imageFile);
            categoryRepository.save(category);
            return categoryToResourceConverter.convert(category);
        } catch (Exception e) {
            log.error(format("An error occurred with your category update." +
                    "Possible reason: %s", e.getLocalizedMessage()));
            throw new CustomNotFoundException("Category could not be updated");
        }
    }

    public Category findCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).orElseThrow(
                ()-> new CustomNotFoundException("Category with name: %s" + categoryName + " does not exist"));
    }

    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                ()-> new CustomNotFoundException("Category for product not found"));
    }

    public void deleteCategoryById(Long categoryId, HttpServletRequest httpServletRequest) {
        User vendor = credentialService.getUserAccount(httpServletRequest);
        credentialService.validateUser(vendor, List.of("VENDOR"));
        categoryRepository.deleteById(categoryId);
    }
}
