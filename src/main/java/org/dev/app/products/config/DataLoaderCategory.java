package org.dev.app.products.config;

import org.dev.app.products.entity.Category;
import org.dev.app.products.entity.CategoryType;
import org.dev.app.products.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataLoaderCategory implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataLoaderCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        List<Category> missingCategories = Arrays.stream(CategoryType.values())
                .filter(categoryType -> !categoryRepository.existsByName(categoryType))
                .map(categoryType -> {
                    Category category = new Category();
                    category.setName(categoryType);
                    return category;
                })
                .toList();

        if (!missingCategories.isEmpty()) {
            categoryRepository.saveAll(missingCategories);
        }
    }
}
