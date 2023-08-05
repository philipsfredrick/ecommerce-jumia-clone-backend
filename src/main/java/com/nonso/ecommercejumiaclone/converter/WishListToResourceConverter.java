package com.nonso.ecommercejumiaclone.converter;

import com.nonso.ecommercejumiaclone.entities.WishList;
import com.nonso.ecommercejumiaclone.dto.response.WishListResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public class WishListToResourceConverter {

    private final ProductToResourceConverter productToResourceConverter;

    public WishListResource convert(WishList wishList) {
        return WishListResource
                .builder()
                .wishListId(wishList.getId())
                .productResources(wishList.getProducts().parallelStream().map(productToResourceConverter::convert).collect(toSet()))
                .createdAt(wishList.getCreatedAt())
                .build();
    }
}
