package org.dev.app.products.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.dev.app.products.entity.CategoryType;

import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        Boolean available,
        Long categoryId,
        CategoryType categoryName
){

}
