package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.converter.ProductToResourceConverter;
import com.nonso.ecommercejumiaclone.entities.Category;
import com.nonso.ecommercejumiaclone.entities.Product;
import com.nonso.ecommercejumiaclone.entities.User;
import com.nonso.ecommercejumiaclone.exception.CustomNotFoundException;
import com.nonso.ecommercejumiaclone.exception.ProductServiceException;
import com.nonso.ecommercejumiaclone.dto.request.ProductRequest;
import com.nonso.ecommercejumiaclone.dto.response.PaginatedProductDetailResource;
import com.nonso.ecommercejumiaclone.dto.response.ProductResource;
import com.nonso.ecommercejumiaclone.repository.CategoryRepository;
import com.nonso.ecommercejumiaclone.repository.ProductRepository;
import com.nonso.ecommercejumiaclone.utils.CloudinaryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import java.util.List;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {
    private final CloudinaryService cloudinaryService;
    private final CredentialService credentialService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductToResourceConverter productToResourceConverter;

    @Transactional
    public ProductResource uploadProduct(ProductRequest productRequest, MultipartFile file, HttpServletRequest httpServletRequest) {
        try {
            User vendor = credentialService.getUserAccount(httpServletRequest);
            credentialService.validateUser(vendor, List.of("VENDOR"));

            String imageUrl = cloudinaryService.uploadImage(file);
            Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                    ()-> new CustomNotFoundException("Category not found")
            );
            Product product = Product.builder()
                    .category(category)
                    .quantityInStock(productRequest.getQuantity())
                    .productName(productRequest.getProductName())
                    .description(productRequest.getDescription())
                    .productPrice(productRequest.getProductPrice())
                    .imageUrl(imageUrl)
                    .vendor(vendor)
                    .build();
            productRepository.save(product);
            return productToResourceConverter.convert(product) ;
        } catch (Exception e) {
            log.error(format("An error occurred while uploading product. Please contact support " +
                    "Possible reasons: %s", e.getLocalizedMessage() ));
            throw new ProductServiceException("An error occurred while uploading product. Please contact support");
        }
    }

    @Transactional
    public ProductResource updateProduct(Long productId, ProductRequest productRequest, MultipartFile file,
                                         HttpServletRequest httpServletRequest) {
        try {
            User vendor = credentialService.getUserAccount(httpServletRequest);
            credentialService.validateUser(vendor, List.of("VENDOR"));
            String imageUrl = cloudinaryService.uploadImage(file);
            Product product = productRepository.findByIdAndCategoryId(productId, productRequest.getCategoryId())
                    .orElseThrow(()-> new CustomNotFoundException("Product not found")
                    );
            product.setProductName(productRequest.getProductName());
            product.setQuantityInStock(productRequest.getQuantity());
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


    public PaginatedProductDetailResource getProductsByNameOrPrice(
            Integer page, Integer size, String productName, BigDecimal productPrice,
            HttpServletRequest httpServletRequest) {
       try {
           User user = credentialService.getUserAccount(httpServletRequest);
           credentialService.validateUser(user, List.of("USER"));

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

    public PaginatedProductDetailResource viewAllProducts(Integer page, Integer size, HttpServletRequest httpServletRequest) {
        try {
            User user = credentialService.getUserAccount(httpServletRequest);
            credentialService.validateUser(user, List.of("USER", "VENDOR"));
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
