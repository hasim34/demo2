package com.demov2.collab.Service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.demov2.collab.Dto.OrderDto;
import com.demov2.collab.Dto.OrderItemDto;
import com.demov2.collab.Entity.Customer;
import com.demov2.collab.Repository.CustomerRepository;
import com.demov2.collab.Repository.OrderItemsRepository;
import com.demov2.collab.Repository.OrderRepository;
import com.demov2.collab.Exception.NoUserFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final CustomerRepository customerRepository;

    public String createOrder(OrderDto orderDto) {
        Optional<Customer> customer = customerRepository.findById(orderDto.getCustomerId());
        if(customer.isEmpty()) throw new NoUserFoundException("No user for the id "+orderDto.getCustomerId()); 
        // List<Long> productIds = new ArrayList<>();
        for(OrderItemDto orderDto2 : orderDto.getItems()){
           continue;
        }
        return "Hi";
    }
}
