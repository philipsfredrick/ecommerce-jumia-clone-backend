package com.nonso.ecommercejumiaclone.payload.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryName;
    private String description;
    private String imageUrl;
}
