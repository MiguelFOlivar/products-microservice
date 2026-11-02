package org.dev.app.products.exception;

import org.springframework.http.HttpStatus;

public class InvalidCategoryException extends BusinessException {

    public InvalidCategoryException() {
        super(
                "El nombre de la categoría es obligatorio",
                HttpStatus.BAD_REQUEST
        );
    }
}
