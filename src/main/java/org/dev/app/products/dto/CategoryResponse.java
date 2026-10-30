package org.dev.app.products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dev.app.products.entity.Category;
import org.dev.app.products.entity.CategoryType;

@Getter
@AllArgsConstructor
public class CategoryResponse {
    private final Long id;
    private final CategoryType name;

    public static CategoryResponse fromEntity(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
