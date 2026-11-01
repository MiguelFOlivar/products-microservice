package org.dev.app.products.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dev.app.products.dto.ProductAdminResponse;
import org.dev.app.products.dto.ProductRequest;
import org.dev.app.products.dto.ProductResponse;
import org.dev.app.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ProductAdminResponse>> getAllForAdmin(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllAdmin());
    }

    @PostMapping
    public ResponseEntity<ProductAdminResponse> create(@Valid @RequestBody ProductRequest request){
        return ResponseEntity.status(HttpsURLConnection.HTTP_CREATED).body(productService.create(request));
    }

    @PutMapping
    public ResponseEntity<ProductResponse> update(@Valid @RequestParam Long id, @RequestBody ProductRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productService.deactivate(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
