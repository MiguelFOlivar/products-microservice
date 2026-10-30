package org.dev.app.products.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryTest {

    @Test
    void shouldContainExpectedCategoryTypes() {
        List<CategoryType> expectedTypes = List.of(
                CategoryType.ELECTRONICS,
                CategoryType.CLOTHING,
                CategoryType.HOME_KITCHEN,
                CategoryType.BEAUTY_HEALTH,
                CategoryType.TECHNOLOGY
        );

        assertEquals(expectedTypes, Arrays.asList(CategoryType.values()));
    }

    @Test
    void shouldPersistCategoryNameAsStringEnum() throws NoSuchFieldException {
        Field nameField = Category.class.getDeclaredField("name");
        Enumerated enumerated = nameField.getAnnotation(Enumerated.class);

        assertNotNull(enumerated);
        assertEquals(EnumType.STRING, enumerated.value());
    }
}
