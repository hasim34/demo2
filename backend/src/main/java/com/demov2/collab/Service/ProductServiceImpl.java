package com.demov2.collab.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.demov2.collab.Entity.Product ;
import com.demov2.collab.Repository.ProductRepository;
import com.demov2.collab.Service.ProductService;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional 
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> createMulProduct(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public Product updateProduct(Long id, Product product){
        return product;
    }

    @Override
    public void deleteProduct(Long id){

    }
};
