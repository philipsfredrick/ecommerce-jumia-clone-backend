package com.nonso.ecommercejumiaclone.converter;

import com.nonso.ecommercejumiaclone.entities.Category;
import com.nonso.ecommercejumiaclone.dto.response.CategoryResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryToResourceConverter {

    public CategoryResource convert(Category category) {
        return CategoryResource
                .builder()
                .categoryId(category.getId())
                .categoryName(category.getCategoryName())
                .imageUrl(category.getImageUrl())
                .createdAt(category.getCreatedAt())
                .build();
    }
}
