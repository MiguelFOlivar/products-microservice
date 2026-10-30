package org.dev.app.products.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dev.app.products.entity.CategoryType;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequest {

    @NotNull(message = "Category name is required")
    private CategoryType name;
}
