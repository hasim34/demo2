package com.demov2.collab.Service;

import org.springframework.stereotype.Service;

import com.demov2.collab.Repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class ProductService {
    private final ProductRepository productRepository;

    
}
