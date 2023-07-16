package com.nonso.ecommercejumiaclone.service.impl;

import com.nonso.ecommercejumiaclone.config.security.Principal;
import com.nonso.ecommercejumiaclone.converter.ProductToResourceConverter;
import com.nonso.ecommercejumiaclone.entities.Category;
import com.nonso.ecommercejumiaclone.entities.Product;
import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import com.nonso.ecommercejumiaclone.exception.CustomNotFoundException;
import com.nonso.ecommercejumiaclone.exception.ProductServiceException;
import com.nonso.ecommercejumiaclone.exception.UnAuthorizedException;
import com.nonso.ecommercejumiaclone.dto.request.ProductRequest;
import com.nonso.ecommercejumiaclone.dto.response.PaginatedProductDetailResource;
import com.nonso.ecommercejumiaclone.dto.response.ProductResource;
import com.nonso.ecommercejumiaclone.repository.CategoryRepository;
import com.nonso.ecommercejumiaclone.repository.ProductRepository;
import com.nonso.ecommercejumiaclone.repository.UserRepository;
import com.nonso.ecommercejumiaclone.service.CredentialService;
import com.nonso.ecommercejumiaclone.service.ProductService;
import com.nonso.ecommercejumiaclone.utils.CloudinaryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import java.util.List;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final CredentialService credentialService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductToResourceConverter productToResourceConverter;

    @Override
    @Transactional
    public ProductResource uploadProduct(ProductRequest productRequest, MultipartFile file) {
        try {
            String userEmail = Principal.getLoggedInUserDetails();
            User vendor = userRepository.findByEmail(userEmail).orElseThrow(
                    ()-> new UsernameNotFoundException("User with " + userEmail + " not found")
            );
            if (vendor.getRole().name().equalsIgnoreCase(UserRole.USER.name())) {
                throw new UnAuthorizedException("Unauthorised access for this action");
            }

            String imageUrl = cloudinaryService.uploadImage(file);
            Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                    ()-> new CustomNotFoundException("Category not found")
            );

            Product product = Product.builder()
                    .category(category)
                    .quantity(productRequest.getQuantity())
                    .productName(productRequest.getProductName())
                    .productPrice(productRequest.getProductPrice())
                    .imageUrl(imageUrl)
                    .vendor(vendor)
                    .build();
            productRepository.save(product);
            return productToResourceConverter.convert(product) ;
        } catch (Exception e) {
            log.error(format("An error occurred while uploading product. Please contact support" +
                    "Possible reasons: %s", e.getLocalizedMessage() ));
            throw new ProductServiceException("An error occurred while uploading product. Please contact support");
        }
    }

    @Override
    @Transactional
    public ProductResource updateProduct(Long productId, ProductRequest productRequest, MultipartFile file) {
        try {
            String userEmail = Principal.getLoggedInUserDetails();
            Product product = productRepository.findByIdAndCategoryId(productId, productRequest.getCategoryId()).orElseThrow(
                    ()-> new CustomNotFoundException("Product not found")
            );
            User vendor = userRepository.findByEmail(userEmail).orElseThrow(
                    ()-> new UsernameNotFoundException("User with " + userEmail + " not found")
            );
            if (vendor.getRole().name().equalsIgnoreCase(UserRole.USER.name())) {
                throw new Exception("Unauthorised");
            }

            String imageUrl = cloudinaryService.uploadImage(file);
            product.setProductName(productRequest.getProductName());
            product.setQuantity(productRequest.getQuantity());
            product.setProductPrice(productRequest.getProductPrice());
            product.setImageUrl(imageUrl);
            product.setVendor(vendor);
            productRepository.save(product);
            return productToResourceConverter.convert(product);
        } catch (Exception e) {
            log.error(format("An error occurred while doing product update. Please contact support" +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new ProductServiceException("An error occurred during product update. Please contact support");
        }
    }

    @Override
    public PaginatedProductDetailResource getProductsByNameOrPrice(Integer page, Integer size, String productName, BigDecimal productPrice,
                                                                   HttpServletRequest httpServletRequest) {
       try {
//           String userEmail =  credentialService.getUser(httpServletRequest);
//           User user = userRepository.findUserByEmailAndRole(userEmail, UserRole.USER);
//           if (user.getRole().name().equalsIgnoreCase(UserRole.VENDOR.name())) {
//               throw new UnAuthorizedException("Unauthorized access");
//           }
           User user = credentialService.getUser(httpServletRequest);
           Page<Product> products = retrieveAllProducts(page, size, productName, productPrice);

           List<ProductResource> productDetailResourceList = products.getContent()
                   .stream().map(productToResourceConverter::convert).toList();
           return PaginatedProductDetailResource
                   .builder()
                   .productDetailResources(productDetailResourceList)
                   .currentPage(products.getPageable().getPageNumber() + 1)
                   .totalElements(products.getTotalElements())
                   .totalPage(products.getTotalPages())
                   .build();
       } catch (Exception e) {
           log.error(format("An error occurred while retrieving products. Please contact support" +
                   "Possible reasons: %s", e.getLocalizedMessage()));
           throw new ProductServiceException("An error occurred during product update. Please contact support");
       }
    }

    @Override
    public PaginatedProductDetailResource viewAllProducts(Integer page, Integer size) {
        try {
            String userEmail = Principal.getLoggedInUserDetails();
            User user = userRepository.findUserByEmailAndRole(userEmail, UserRole.VENDOR);
            if (user.getRole().name().equalsIgnoreCase(UserRole.USER.name())) {
                throw new UnAuthorizedException("Unauthorized access");
            }
            Page<Product> products = AllProducts(page, size);

            List<ProductResource> productDetailResourceList = products.getContent()
                    .stream().map(productToResourceConverter::convert).toList();
            return PaginatedProductDetailResource
                    .builder()
                    .productDetailResources(productDetailResourceList)
                    .currentPage(products.getPageable().getPageNumber() + 1)
                    .totalElements(products.getTotalElements())
                    .totalPage(products.getTotalPages())
                    .build();
        } catch (Exception e) {
            log.error(format("An error occurred while doing viewing products. Please contact support" +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new ProductServiceException("An error occurred during product update. Please contact support");
        }
    }

    private Page<Product> retrieveAllProducts(Integer page, Integer size, String productName, BigDecimal productPrice) {
        return productRepository.findProductsByProductNameOrProductPrice(
                productName, productPrice, getPageable(page, size));
    }

    private Page<Product> AllProducts(Integer page, Integer size) {
        return productRepository.findAll(getPageable(page, size));
    }

    private Pageable getPageable(Integer page, Integer size) {
        size = size < 1 || size > 10 ? 5 : size;
        page = page < 1 ? 1 : page;
        return PageRequest.of(--page, size, Sort.Direction.DESC, "createdAt");
    }
}
