package org.dev.app.products.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class, DataIntegrityViolationException.class, Exception.class})
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, HttpServletRequest request){
        return switch (ex){
            case BusinessException e ->
                buildResponse(e.getStatus(), "Error de OpenTracing / Negocio", e.getMessage(), request);

            case DataIntegrityViolationException e ->
                buildResponse(HttpStatus.CONFLICT, "Conflicto de integridad", e.getMessage(), request);

            default ->
                buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado", ex.getMessage(), request);
        };
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                    HttpServletRequest request){
        List<String> validationErrors = ex.getBindingResult()
        .getFieldErrors().stream().map(error -> error.getField() + " : " + error.getDefaultMessage())
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error de validación",
                HttpStatus.BAD_REQUEST.value(), "Los datos enviados no son validos",
                request.getRequestURI(),validationErrors));
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String title, String detail, HttpServletRequest request) {
        return ResponseEntity.status(status).body(new ErrorResponse(title, status.value(), detail, request.getRequestURI(),null));
    }

}
