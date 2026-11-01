package org.dev.app.products.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BusinessException{

    public ProductNotFoundException(Long id) {
        super("El producto con id: " + id + " no existe.", HttpStatus.NOT_FOUND);
    }
}
