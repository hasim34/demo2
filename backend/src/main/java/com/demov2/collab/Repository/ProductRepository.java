package com.demov2.collab.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demov2.collab.Entity.Product;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Long>{
    
}
