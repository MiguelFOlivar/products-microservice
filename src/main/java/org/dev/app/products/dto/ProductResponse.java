package org.dev.app.products.dto;

import org.dev.app.products.entity.CategoryType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
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
){

}
