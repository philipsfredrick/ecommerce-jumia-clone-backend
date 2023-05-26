package com.nonso.ecommercejumiaclone.controllers;

import com.google.gson.Gson;
import com.nonso.ecommercejumiaclone.payload.request.ProductRequest;
import com.nonso.ecommercejumiaclone.payload.response.PaginatedProductDetailResource;
import com.nonso.ecommercejumiaclone.payload.response.ProductResource;
import com.nonso.ecommercejumiaclone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResource> uploadProduct(
            @RequestParam("addProduct") String addProduct,
            @RequestParam("file") MultipartFile file) {
        ProductRequest productRequest = new Gson().fromJson(addProduct, ProductRequest.class);

        return new ResponseEntity<>(productService.uploadProduct(productRequest, file), CREATED);
    }


    @PutMapping("/{productId}")
    public ResponseEntity<ProductResource> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestParam("addProduct") String addProduct,
            @RequestParam("file") MultipartFile file) {
        ProductRequest productRequest = new Gson().fromJson(addProduct, ProductRequest.class);

        return new ResponseEntity<>(productService.updateProduct(productId, productRequest, file), CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedProductDetailResource> getAllProductsByNameOrPrice(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer size,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "productPrice", required = false) BigDecimal productPrice) {
        return new ResponseEntity<>(productService.getProductsByNameOrPrice(page, size, productName, productPrice), OK);
    }

    @GetMapping("/vendor")
    public ResponseEntity<PaginatedProductDetailResource> viewAllProducts(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return new ResponseEntity<>(productService.viewAllProducts(page, size), OK);
    }
}
