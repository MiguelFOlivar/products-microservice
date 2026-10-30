package org.dev.app.products.config;

import org.dev.app.products.entity.Category;
import org.dev.app.products.entity.CategoryType;
import org.dev.app.products.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DataLoaderCategoryTest {

    @SuppressWarnings("unchecked")
    @Test
    void shouldSaveOnlyMissingCategoriesOnStartup() throws Exception {
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        when(categoryRepository.existsByName(CategoryType.ELECTRONICS)).thenReturn(true);
        when(categoryRepository.existsByName(CategoryType.CLOTHING)).thenReturn(false);
        when(categoryRepository.existsByName(CategoryType.HOME_KITCHEN)).thenReturn(true);
        when(categoryRepository.existsByName(CategoryType.BEAUTY_HEALTH)).thenReturn(false);
        when(categoryRepository.existsByName(CategoryType.TECHNOLOGY)).thenReturn(false);

        DataLoaderCategory dataLoaderCategory = new DataLoaderCategory(categoryRepository);

        dataLoaderCategory.run();

        ArgumentCaptor<List<Category>> captor = ArgumentCaptor.forClass(List.class);
        verify(categoryRepository).saveAll(captor.capture());

        EnumSet<CategoryType> savedCategoryNames = captor.getValue().stream()
                .map(Category::getName)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(CategoryType.class)));

        assertEquals(EnumSet.of(CategoryType.CLOTHING, CategoryType.BEAUTY_HEALTH, CategoryType.TECHNOLOGY), savedCategoryNames);
    }

    @Test
    void shouldNotSaveWhenAllCategoriesAlreadyExist() throws Exception {
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        for (CategoryType categoryType : CategoryType.values()) {
            when(categoryRepository.existsByName(categoryType)).thenReturn(true);
        }

        DataLoaderCategory dataLoaderCategory = new DataLoaderCategory(categoryRepository);

        dataLoaderCategory.run();

        verify(categoryRepository, never()).saveAll(anyList());
    }
}
