package org.dev.app.products.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BusinessException{

    public CategoryNotFoundException(Long id) {
        super("La categorìa con id: " +id +" no fue encontrada", HttpStatus.NOT_FOUND);
    }
}
