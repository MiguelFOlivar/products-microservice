package org.dev.app.products.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String title,
        int status,
        String detail,
        String path,
        Instant instant,
        List<String> errors
) {
    public ErrorResponse(String title, int status, String detail, String path, List<String> errors) {
        this(title, status, detail, path, Instant.now(), null);
    }
}
