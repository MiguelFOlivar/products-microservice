package org.dev.app.products.exception;

import org.springframework.http.HttpStatus;

public class OutOfStockException extends BusinessException{
    public OutOfStockException(Long id) {
        super("Sin stock disponible para el producto:  " + id , HttpStatus.CONFLICT);
    }
}
