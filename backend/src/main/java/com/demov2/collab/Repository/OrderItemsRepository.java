package com.demov2.collab.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demov2.collab.Entity.OrderItem;

public interface OrderItemsRepository extends JpaRepository<OrderItem,Long> {
    
}
