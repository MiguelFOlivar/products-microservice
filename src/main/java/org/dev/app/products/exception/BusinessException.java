package org.dev.app.products.exception;

import org.springframework.http.HttpStatus;

// Clase base para las excepciones de negocio
// permite que cada excepción controle su código HTTP

public abstract class BusinessException extends RuntimeException{
    private final HttpStatus status;

    protected BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }
}
