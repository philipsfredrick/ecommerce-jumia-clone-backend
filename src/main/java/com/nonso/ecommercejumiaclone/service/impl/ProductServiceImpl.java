package com.nonso.ecommercejumiaclone.service.impl;

import com.nonso.ecommercejumiaclone.config.security.JwtService;
import com.nonso.ecommercejumiaclone.config.security.Principal;
import com.nonso.ecommercejumiaclone.entities.Category;
import com.nonso.ecommercejumiaclone.entities.Product;
import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.exceptions.CustomNotFoundException;
import com.nonso.ecommercejumiaclone.payload.request.ProductDto;
import com.nonso.ecommercejumiaclone.payload.response.ApiResponse;
import com.nonso.ecommercejumiaclone.repository.CategoryRepository;
import com.nonso.ecommercejumiaclone.repository.ProductRepository;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
import com.nonso.ecommercejumiaclone.service.ProductService;
import com.nonso.ecommercejumiaclone.utils.CloudinaryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;

    private final JwtService jwtService;

    /**
     * @param productDto
     * @param
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponse<Product> uploadProduct(ProductDto productDto, MultipartFile file) throws Exception {
        String userEmail = Principal.getLoggedInUserDetails();
        User vendor = userRepository.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User with " + userEmail + " not found"));

        if (vendor.getRole().name().equalsIgnoreCase(UserRole.USER.name())) {
            throw new Exception("Unauthorised");
        }

        String logoUrl = cloudinaryService.uploadImage(file);
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(()-> new CustomNotFoundException("Category not found"));
        Product product = Product.builder()
                .category(category)
                .quantity(productDto.getQuantity())
                .productName(productDto.getProductName())
                .productPrice(productDto.getPrice())
                .logo(logoUrl)
                .user(vendor)
                .build();

        productRepository.save(product);
        return new ApiResponse<>("Product created", true, product);
    }

    @Override
    public ApiResponse<Product> updateProduct(Long productId, ProductDto productDto, MultipartFile file) throws Exception {
        String userEmail = Principal.getLoggedInUserDetails();

        Product product = productRepository.findByIdAndCategoryId(productId, productDto.getCategoryId()).orElseThrow(()-> new CustomNotFoundException("Product not found"));
        User vendor = userRepository.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User with " + userEmail + " not found"));
        if (vendor.getRole().name().equalsIgnoreCase(UserRole.USER.name())) {
            throw new Exception("Unauthorised");
        }

        String logoUrl = cloudinaryService.uploadImage(file);

        product.setProductName(productDto.getProductName());
        product.setQuantity(productDto.getQuantity());
        product.setProductPrice(productDto.getPrice());
        product.setLogo(logoUrl);
        product.setUser(vendor);
        productRepository.save(product);

        return new ApiResponse<>("Product updated", true, product);
    }

    /**
     * @param offSet
     * @param pageSize
     * @return
     */
    @Override
    public Page<Product> viewAllProducts(int offSet, int pageSize, String productName, BigDecimal productPrice) {
        Pageable pageable = PageRequest.of(offSet, pageSize);
        Page<Product> products = productRepository.listProducts(productName, productPrice, pageable);
//        List<Product> productList = new ArrayList<>();
//        products.forEach(productList::add);
        return products;
    }

}
