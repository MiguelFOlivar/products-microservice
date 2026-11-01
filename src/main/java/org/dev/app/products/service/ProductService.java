package org.dev.app.products.service;

import lombok.RequiredArgsConstructor;
import org.dev.app.products.dto.ProductRequest;
import org.dev.app.products.dto.ProductResponse;
import org.dev.app.products.entity.Category;
import org.dev.app.products.entity.Product;
import org.dev.app.products.exception.CategoryNotFoundException;
import org.dev.app.products.exception.ProductNotFoundException;
import org.dev.app.products.repository.CategoryRepository;
import org.dev.app.products.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductResponse> findAll(){
        return productRepository.findAllByActiveTrue().stream()
                .map(this::toResponse).toList();
    }

    public ProductResponse findById(Long productId){
        Product product = productRepository
                .findByIdAndActiveTrue(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return toResponse(product);
    }

    @Transactional
    public ProductResponse create(ProductRequest productRequest){
        Category category = categoryRepository.findById(productRequest.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(productRequest.categoryId()));
        Product product = new Product();
        applyChanges(product, category, productRequest);
        return toResponse(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest productRequest){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(productRequest.categoryId()));
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException(id));
        applyChanges(product, category, productRequest);
        return toResponse(product);
    }


    // Aquì usamos métodos auxiliares para evitar repetir código
    //Mapper Product -> ProductResponse
    private ProductResponse toResponse(Product product){
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(),
                product.getPrice(), product.getStock(), product.isActive(), product.getCategory().getId(),
                product.getCategory().getName(), product.getCreatedAt(),
                product.getUpdatedAt());
    }

    //Construye el objeto Product a guardar
    private void applyChanges(Product product, Category category, ProductRequest productRequest){
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setStock(productRequest.stock());
        product.setCategory(category);
        if (productRequest.active() !=null){
            product.setActive(productRequest.active());
        }
    }
}
