package org.dev.app.products.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "El nombre del producto es obligatorio")
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String name,

        @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
        String description,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
        @Digits(integer = 8, fraction = 2, message = "El precio debe tener máximo 8 dígitos enteros y 2 decimales")
        BigDecimal price,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        Boolean active, //Opcional por si en Admin quiere activar o desactivar un producto

        @NotNull(message = "La categoria es obligatoria")
        @Positive(message = "El Id de la categoría debe ser positivo")
        Long categoryId
) {

}
