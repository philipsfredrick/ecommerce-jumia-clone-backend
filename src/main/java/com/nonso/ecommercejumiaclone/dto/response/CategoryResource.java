package com.nonso.ecommercejumiaclone.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResource implements Serializable {

    @Serial
    private static final long serialVersionUID = -3455695106471013134L;

    @NotNull(message = "category id must not be null")
    @JsonProperty("categoryId")
    private Long categoryId;

    @NotBlank(message = "category name must not be blank")
    @JsonProperty("categoryName")
    private String categoryName;

    @NotBlank(message = "created date must not be blank")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @NotBlank(message = "image must not be blank")
    @JsonProperty("imageUrl")
    private String imageUrl;
}
