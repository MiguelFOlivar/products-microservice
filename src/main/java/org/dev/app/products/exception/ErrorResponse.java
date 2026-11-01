package org.dev.app.products.exception;

import java.time.Instant;
import java.util.List;

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
