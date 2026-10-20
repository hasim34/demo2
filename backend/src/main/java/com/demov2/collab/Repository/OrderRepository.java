package com.demov2.collab.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demov2.collab.Entity.Orders;

public interface OrderRepository extends JpaRepository<Orders,Long> {
    
}
