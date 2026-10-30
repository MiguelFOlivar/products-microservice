package org.dev.app.products.dto;

import org.dev.app.products.entity.Category;
import org.dev.app.products.entity.CategoryType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryResponseTest {

    @Test
    void shouldMapCategoryEntityToResponseDto() {
        Category category = new Category();
        category.setId(10L);
        category.setName(CategoryType.ELECTRONICS);

        CategoryResponse response = CategoryResponse.fromEntity(category);

        assertEquals(10L, response.getId());
        assertEquals(CategoryType.ELECTRONICS, response.getName());
    }
}
