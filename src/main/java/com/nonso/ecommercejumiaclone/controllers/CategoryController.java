package com.nonso.ecommercejumiaclone.controllers;

import com.google.gson.Gson;
import com.nonso.ecommercejumiaclone.dto.request.CategoryRequest;
import com.nonso.ecommercejumiaclone.dto.response.CategoryResource;
import com.nonso.ecommercejumiaclone.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResource> createCategory(
            HttpServletRequest httpServletRequest,
            @RequestParam("addCategory") String addCategory,
            @RequestParam("file")MultipartFile file) {
        CategoryRequest request = new Gson().fromJson(addCategory, CategoryRequest.class);
        return new ResponseEntity<>(categoryService.createCategory(request, file, httpServletRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResource>> listCategory() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResource> updateCategory(
            HttpServletRequest httpServletRequest,
            @PathVariable("categoryId") Long categoryId,
            @RequestParam("category") String category,
            @RequestParam("file")MultipartFile file) {
        CategoryRequest request = new Gson().fromJson(category, CategoryRequest.class);
        return new ResponseEntity<>(categoryService.updateCategory(categoryId, request, file, httpServletRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(HttpServletRequest httpServletRequest, @PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategoryById(categoryId, httpServletRequest);
    }
}
