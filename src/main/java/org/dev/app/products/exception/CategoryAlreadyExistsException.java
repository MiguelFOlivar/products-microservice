package org.dev.app.products.exception;

import org.dev.app.products.entity.CategoryType;
import org.springframework.http.HttpStatus;

public class CategoryAlreadyExistsException extends BusinessException {

    public CategoryAlreadyExistsException(CategoryType name) {
        super("La categoría " + name + " ya existe", HttpStatus.CONFLICT);
    }
}
