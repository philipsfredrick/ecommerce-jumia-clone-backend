package com.nonso.ecommercejumiaclone.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest implements Serializable {

    @NotBlank(message = "category name must not be blank")
    @JsonProperty("category_name")
    private String categoryName;
    @NotBlank(message = "category image must not be blank")
    @JsonProperty("image_url")
    private String imageUrl;
}
