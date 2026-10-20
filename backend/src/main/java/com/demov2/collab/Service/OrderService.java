package com.demov2.collab.Service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.demov2.collab.DTO.OrderDto;
import com.demov2.collab.DTO.OrderItemDto;
import com.demov2.collab.Entity.Customer;
import com.demov2.collab.Repository.CustomerRepository;
import com.demov2.collab.Repository.OrderItemsRepository;
import com.demov2.collab.Repository.OrderRepository;
import com.demov2.collab.Repository.ProductRepository;
import com.demov2.collab.Exception.NoUserFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public String createOrder(OrderDto orderDto) {
        Optional<Customer> customer = customerRepository.findById(orderDto.getCustomerId());
        if(customer.isEmpty()) throw new NoUserFoundException("No user for the id "+orderDto.getCustomerId()); 
        // List<Long> productIds = new ArrayList<>();
        for(OrderItemDto orderDto2 : orderDto.getItems()){
            if(!productRepository.existsById(orderDto2.getProductId())){
                throw new NoUserFoundException("no found product for id "+orderDto2.getProductId());
            }
            // Product product = productResponse
        }

        return "Hi";
    }
}
