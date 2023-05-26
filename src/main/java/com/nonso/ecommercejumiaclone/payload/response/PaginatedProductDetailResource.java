package com.nonso.ecommercejumiaclone.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedProductDetailResource implements Serializable {

    @Serial
    private static final long serialVersionUID = 5162594162557354013L;

    @NotNull(message = "content must have a value")
    @JsonProperty("content")
    private List<ProductResource> productDetailResources;

    @NotNull(message = "current_page must have a value")
    @JsonProperty("current_page")
    private Integer currentPage;

    @NotNull(message = "current_page must have a value")
    @JsonProperty("total_page")
    private Integer totalPage;

    @NotNull(message = "total_elements must have a value")
    @JsonProperty("total_elements")
    private Long totalElements;

}
