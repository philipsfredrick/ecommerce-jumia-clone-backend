package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.payload.request.ProductRequest;
import com.nonso.ecommercejumiaclone.payload.response.PaginatedProductDetailResource;
import com.nonso.ecommercejumiaclone.payload.response.ProductResource;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface ProductService {

    ProductResource uploadProduct(ProductRequest productRequest, MultipartFile file);

    ProductResource updateProduct(Long productId, ProductRequest productRequest, MultipartFile file);

//    Page<Product> retrieveAllProducts(Integer page, Integer size, String productName, BigDecimal productPrice);

    PaginatedProductDetailResource getProductsByNameOrPrice(Integer page, Integer size, String productName, BigDecimal productPrice);
    PaginatedProductDetailResource viewAllProducts(Integer page, Integer size);


}
