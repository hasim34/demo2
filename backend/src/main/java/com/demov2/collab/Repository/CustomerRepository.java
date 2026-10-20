package com.demov2.collab.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demov2.collab.Entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    
}
