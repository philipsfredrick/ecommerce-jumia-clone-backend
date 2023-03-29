package com.nonso.ecommercejumiaclone.controllers;

import com.google.gson.Gson;
import com.nonso.ecommercejumiaclone.entities.Category;
import com.nonso.ecommercejumiaclone.entities.Product;
import com.nonso.ecommercejumiaclone.payload.request.CategoryDto;
import com.nonso.ecommercejumiaclone.payload.request.ProductDto;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ApiResponse> uploadProduct(@RequestParam("addProduct") String addProduct, @RequestParam("file") MultipartFile file) throws Exception {
        ProductDto productDto = new Gson().fromJson(addProduct, ProductDto.class);

        return new ResponseEntity<>(productService.uploadProduct(productDto, file), HttpStatus.CREATED);
    }


    @PutMapping("/products/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long productId, @RequestParam("addProduct") String addProduct, @RequestParam("file") MultipartFile file) throws Exception {
        ProductDto productDto = new Gson().fromJson(addProduct, ProductDto.class);

        return new ResponseEntity<>(productService.updateProduct(productId, productDto, file), HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public Page<Product> viewAllProducts(
            @RequestParam(value = "offSet", defaultValue = "1") int offSet,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "productPrice", required = false) BigDecimal productPrice) {
        return productService.viewAllProducts(offSet, pageSize, productName, productPrice);
    }
}
