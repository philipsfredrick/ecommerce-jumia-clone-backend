package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.entities.Category;
import com.nonso.ecommercejumiaclone.entities.Product;
import com.nonso.ecommercejumiaclone.payload.request.ProductDto;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ApiResponse<Product> uploadProduct(ProductDto productDto, MultipartFile file) throws Exception;

    ApiResponse<Product> updateProduct(Long productId, ProductDto productDto, MultipartFile file) throws Exception;

    Page<Product> viewAllProducts(int offSet, int pageSize, String productName, BigDecimal productPrice);


}
