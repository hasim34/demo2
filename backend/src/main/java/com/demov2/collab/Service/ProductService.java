package com.demov2.collab.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.demov2.collab.Entity.Product ;
import com.demov2.collab.Repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> createMulProduct(List<Product> products) {
        return productRepository.saveAll(products);
    }
};
