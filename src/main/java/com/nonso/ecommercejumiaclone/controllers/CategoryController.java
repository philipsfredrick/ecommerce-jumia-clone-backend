package com.nonso.ecommercejumiaclone.controllers;

import com.google.gson.Gson;
import com.nonso.ecommercejumiaclone.entities.Category;
import com.nonso.ecommercejumiaclone.payload.request.CategoryDto;
import com.nonso.ecommercejumiaclone.payload.request.UserSignUpRequest;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse> createCategory(@RequestParam("addCategory") String addCategory, @RequestParam("file")MultipartFile file) throws IOException {
        CategoryDto request = new Gson().fromJson(addCategory, CategoryDto.class);
        return new ResponseEntity<>(categoryService.createCategory(request, file), HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> listCategory() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PutMapping("categories/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestParam("category") String category, @RequestParam("file")MultipartFile file) throws IOException {
        CategoryDto request = new Gson().fromJson(category, CategoryDto.class);
        return new ResponseEntity<>(categoryService.updateCategory(categoryId, request, file), HttpStatus.OK);
    }
}
