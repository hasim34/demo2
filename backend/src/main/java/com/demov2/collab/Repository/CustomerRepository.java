package com.demov2.collab.Repository;

import com.demov2.collab.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer ,Long> {
}
