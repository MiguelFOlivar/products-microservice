package org.dev.app.products.service;

import lombok.RequiredArgsConstructor;
import org.dev.app.products.dto.CategoryRequest;
import org.dev.app.products.dto.CategoryResponse;
import org.dev.app.products.entity.Category;
import org.dev.app.products.exception.CategoryAlreadyExistsException;
import org.dev.app.products.exception.CategoryNotFoundException;
import org.dev.app.products.exception.InvalidCategoryException;
import org.dev.app.products.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::fromEntity)
                .toList();
    }

    public CategoryResponse findById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryResponse::fromEntity)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {

        if (request == null || request.getName() == null) {
            throw new InvalidCategoryException();
        }

        if (categoryRepository.existsByName(request.getName())) {
            throw new CategoryAlreadyExistsException(request.getName());
        }

        Category category = new Category();
        category.setName(request.getName());

        Category savedCategory = categoryRepository.save(category);

        return CategoryResponse.fromEntity(savedCategory);
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {

        if (request == null || request.getName() == null) {
            throw new InvalidCategoryException();
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (!category.getName().equals(request.getName())
                && categoryRepository.existsByName(request.getName())) {
            throw new CategoryAlreadyExistsException(request.getName());
        }

        category.setName(request.getName());

        Category updatedCategory = categoryRepository.save(category);

        return CategoryResponse.fromEntity(updatedCategory);
    }

    @Transactional
    public void delete(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        categoryRepository.delete(category);
    }

}
