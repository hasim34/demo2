package com.demov2.collab.Service;

import java.util.List;

import com.demov2.collab.Entity.Product ;


public interface ProductService {

    public List<Product> getAllProducts();

    public Product getProductById(Long id) ;

    public Product createProduct(Product product);

    public List<Product> createMulProduct(List<Product> products) ;
};
