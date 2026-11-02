package org.dev.app.products.service;

import org.dev.app.products.dto.CategoryRequest;
import org.dev.app.products.dto.CategoryResponse;
import org.dev.app.products.entity.Category;
import org.dev.app.products.entity.CategoryType;
import org.dev.app.products.exception.CategoryAlreadyExistsException;
import org.dev.app.products.exception.CategoryNotFoundException;
import org.dev.app.products.exception.InvalidCategoryException;
import org.dev.app.products.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void findAll_ShouldReturnAllCategories() {

        // Arrange: preparar las categorías de prueba
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName(CategoryType.ELECTRONICS);

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName(CategoryType.CLOTHING);

        when(categoryRepository.findAll())
                .thenReturn(List.of(category1, category2));

        // Act: ejecutar el método que queremos probar
        List<CategoryResponse> result = categoryService.findAll();

        // Assert: comprobar que el resultado sea correcto
        assertEquals(2, result.size());
        assertEquals(CategoryType.ELECTRONICS, result.get(0).getName());
        assertEquals(CategoryType.CLOTHING, result.get(1).getName());

        verify(categoryRepository).findAll();
    }

    @Test
    void findById_ShouldReturnCategory_WhenExists() {

        // Arrange: preparar la categoría
        Category category = new Category();
        category.setId(1L);
        category.setName(CategoryType.ELECTRONICS);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        // Act: buscar la categoría
        CategoryResponse result = categoryService.findById(1L);

        // Assert: verificar los resultados
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(CategoryType.ELECTRONICS, result.getName());

        verify(categoryRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenCategoryDoesNotExist() {

        // Arrange: simular que la categoría no existe
        Long categoryId = 99L;

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.empty());

        // Act y Assert: comprobar que se lance la excepción
        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.findById(categoryId)
        );

        // Verificar el mensaje y el código HTTP
        assertTrue(exception.getMessage().contains("99"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        // Verificar que se consultó el repositorio
        verify(categoryRepository).findById(categoryId);
    }

    @Test
    void create_ShouldSaveCategory_WhenNameIsAvailable() {

        // Arrange: preparar la solicitud
        CategoryRequest request = new CategoryRequest();
        request.setName(CategoryType.TECHNOLOGY);

        Category savedCategory = new Category();
        savedCategory.setId(5L);
        savedCategory.setName(CategoryType.TECHNOLOGY);

        when(categoryRepository.existsByName(CategoryType.TECHNOLOGY))
                .thenReturn(false);

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(savedCategory);

        // Act: crear la categoría
        CategoryResponse result = categoryService.create(request);

        // Assert: comprobar el resultado
        assertNotNull(result);
        assertEquals(5L, result.getId());
        assertEquals(CategoryType.TECHNOLOGY, result.getName());

        ArgumentCaptor<Category> captor =
                ArgumentCaptor.forClass(Category.class);

        verify(categoryRepository).save(captor.capture());

        assertEquals(
                CategoryType.TECHNOLOGY,
                captor.getValue().getName()
        );

        verify(categoryRepository)
                .existsByName(CategoryType.TECHNOLOGY);
    }

    @Test
    void create_ShouldThrowException_WhenCategoryAlreadyExists() {

        // Arrange: preparar una categoría que ya existe
        CategoryRequest request = new CategoryRequest();
        request.setName(CategoryType.ELECTRONICS);

        when(categoryRepository.existsByName(CategoryType.ELECTRONICS))
                .thenReturn(true);

        // Act y Assert: comprobar que se lance la excepción
        CategoryAlreadyExistsException exception = assertThrows(
                CategoryAlreadyExistsException.class,
                () -> categoryService.create(request)
        );

        // Verificar el mensaje y el código HTTP
        assertTrue(exception.getMessage().contains("ELECTRONICS"));
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());

        // Verificar que se consultó el repositorio
        verify(categoryRepository).existsByName(CategoryType.ELECTRONICS);

        // Comprobar que nunca se intentó guardar la categoría
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void create_ShouldThrowException_WhenNameIsNull() {

        // Arrange: crear una solicitud sin nombre
        CategoryRequest request = new CategoryRequest();
        request.setName(null);

        // Act y Assert: comprobar la excepción
        InvalidCategoryException exception = assertThrows(
                InvalidCategoryException.class,
                () -> categoryService.create(request)
        );

        // Verificar el código HTTP
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // Verificar que no se utilizó el repositorio
        verifyNoInteractions(categoryRepository);
    }

    @Test
    void update_ShouldUpdateCategory_WhenDataIsValid() {

        // Arrange: preparar la categoría existente
        Category category = new Category();
        category.setId(1L);
        category.setName(CategoryType.ELECTRONICS);

        CategoryRequest request = new CategoryRequest();
        request.setName(CategoryType.TECHNOLOGY);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(categoryRepository.existsByName(CategoryType.TECHNOLOGY))
                .thenReturn(false);

        when(categoryRepository.save(any(Category.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act: ejecutar la actualización
        CategoryResponse result = categoryService.update(1L, request);

        // Assert: comprobar los resultados
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(CategoryType.TECHNOLOGY, result.getName());

        verify(categoryRepository).findById(1L);
        verify(categoryRepository).existsByName(CategoryType.TECHNOLOGY);
        verify(categoryRepository).save(category);
    }

    @Test
    void update_ShouldThrowException_WhenCategoryDoesNotExist() {

        // Arrange: preparar la solicitud
        Long categoryId = 99L;

        CategoryRequest request = new CategoryRequest();
        request.setName(CategoryType.TECHNOLOGY);

        // Simular que la categoría no existe
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.empty());

        // Act y Assert: comprobar la excepción
        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.update(categoryId, request)
        );

        // Verificar el estado HTTP
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        // Verificar que se buscó la categoría
        verify(categoryRepository).findById(categoryId);

        // Comprobar que no se guardó ningún cambio
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void update_ShouldThrowException_WhenNameAlreadyExists() {

        // Arrange: categoría que queremos actualizar
        Category category = new Category();
        category.setId(1L);
        category.setName(CategoryType.ELECTRONICS);

        CategoryRequest request = new CategoryRequest();
        request.setName(CategoryType.TECHNOLOGY);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        // Simular que TECHNOLOGY ya existe
        when(categoryRepository.existsByName(CategoryType.TECHNOLOGY))
                .thenReturn(true);

        // Act y Assert: comprobar el conflicto
        CategoryAlreadyExistsException exception = assertThrows(
                CategoryAlreadyExistsException.class,
                () -> categoryService.update(1L, request)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());

        // Verificar las consultas realizadas
        verify(categoryRepository).findById(1L);
        verify(categoryRepository).existsByName(CategoryType.TECHNOLOGY);

        // Comprobar que no se guardaron cambios
        verify(categoryRepository, never()).save(any(Category.class));

        // Comprobar que se conservó el nombre original
        assertEquals(CategoryType.ELECTRONICS, category.getName());
    }

    @Test
    void delete_ShouldDeleteCategory_WhenExists() {

        // Arrange: preparar una categoría existente
        Long categoryId = 3L;

        Category category = new Category();
        category.setId(categoryId);
        category.setName(CategoryType.HOME_KITCHEN);

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(category));

        // Act: ejecutar la eliminación
        categoryService.delete(categoryId);

        // Assert: comprobar las operaciones realizadas
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).delete(category);
    }

    @Test
    void delete_ShouldThrowException_WhenCategoryDoesNotExist() {

        // Arrange: preparar un ID inexistente
        Long categoryId = 99L;

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.empty());

        // Act y Assert: verificar que se lance la excepción
        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.delete(categoryId)
        );

        // Verificar el estado HTTP de la excepción
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        // Verificar que se buscó la categoría
        verify(categoryRepository).findById(categoryId);

        // Comprobar que no se intentó eliminar nada
        verify(categoryRepository, never()).delete(any(Category.class));
    }

    @Test
    void update_ShouldAllowKeepingSameName() {

        // Arrange: categoría existente
        Category category = new Category();
        category.setId(1L);
        category.setName(CategoryType.ELECTRONICS);

        CategoryRequest request = new CategoryRequest();
        request.setName(CategoryType.ELECTRONICS);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(categoryRepository.save(category))
                .thenReturn(category);

        // Act: actualizar conservando el mismo nombre
        CategoryResponse result = categoryService.update(1L, request);

        // Assert: verificar el resultado
        assertEquals(1L, result.getId());
        assertEquals(CategoryType.ELECTRONICS, result.getName());

        // No debe consultar duplicados porque el nombre no cambió
        verify(categoryRepository, never())
                .existsByName(any(CategoryType.class));

        verify(categoryRepository).save(category);
    }

    @Test
    void update_ShouldThrowException_WhenNameIsNull() {

        // Arrange: preparar una solicitud sin nombre
        CategoryRequest request = new CategoryRequest();
        request.setName(null);

        // Act y Assert: comprobar que se lance la excepción
        InvalidCategoryException exception = assertThrows(
                InvalidCategoryException.class,
                () -> categoryService.update(1L, request)
        );

        // Verificar que la excepción tenga el estado HTTP 400
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // Comprobar que no se consultó ni modificó la base de datos
        verifyNoInteractions(categoryRepository);
    }

}
