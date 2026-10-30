package org.dev.app.products.repository;

import org.dev.app.products.entity.Category;
import org.dev.app.products.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(CategoryType name);
}
