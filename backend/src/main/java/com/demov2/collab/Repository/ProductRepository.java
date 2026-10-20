package com.demov2.collab.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demov2.collab.Entity.Product;

public interface  ProductRepository extends JpaRepository<Product, Long>{
    
}
