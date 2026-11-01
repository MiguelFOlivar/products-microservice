package org.dev.app.products.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.dev.app.products.entity.CategoryType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductAdminResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        boolean active,
        Long categoryId,
        CategoryType categoryName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
