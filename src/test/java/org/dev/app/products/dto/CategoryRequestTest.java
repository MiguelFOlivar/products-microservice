package org.dev.app.products.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.dev.app.products.entity.CategoryType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenNameIsNull() {
        CategoryRequest request = new CategoryRequest();

        Set<ConstraintViolation<CategoryRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertEquals("name", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void shouldPassValidationWhenNameIsPresent() {
        CategoryRequest request = new CategoryRequest();
        request.setName(CategoryType.TECHNOLOGY);

        Set<ConstraintViolation<CategoryRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }
}
