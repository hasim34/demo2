package com.demov2.collab.Service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demov2.collab.DTO.ApiSuccessResponse;
import com.demov2.collab.DTO.OrderDto;
import com.demov2.collab.DTO.OrderItemDto;
import com.demov2.collab.Entity.Customer;
import com.demov2.collab.Entity.OrderItem;
import com.demov2.collab.Entity.Orders;
import com.demov2.collab.Entity.Product;
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

    public ApiSuccessResponse createOrder(OrderDto orderDto) {
        Customer customer = customerRepository.findById(orderDto.getCustomerId()).orElse(null);

        if (customer == null)
            throw new NoUserFoundException("No user for the id " + orderDto.getCustomerId());
        // List<Long> productIds = new ArrayList<>();
        // for(OrderItemDto orderDto2 : orderDto.getItems()){
        // // if(!productRepository.existsById(orderDto2.getProductId())){
        // // throw new NoUserFoundException("no found product for id
        // "+orderDto2.getProductId());
        // // }
        // Product product =
        // productRepository.findById(orderDto2.getProductId()).orElseThrow(() -> new
        // NoUserFoundException("no found product for id "+orderDto2.getProductId()));
        // if(! (product.getAvailableStock() < orderDto2.getQuantity())) throw new
        // NoUserFoundException("NO stock");
        // }
        Orders order = new Orders();
        order.setCustomer(customer);
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orders : orderDto.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            Product product = productRepository.findById(orders.getProductId())
                    .orElseThrow(() -> new NoUserFoundException("no found product for id " + orders.getProductId()));
            if ((product.getAvailableStock() < orders.getQuantity()))
                throw new NoUserFoundException("NO stock");
            orderItem.setProduct(product);
            orderItem.setQuantity(orders.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItems.add(orderItem);
        }
        orderRepository.save(order);
        for(OrderItem orders : orderItems){
            orderItemsRepository.save(orders);
            System.out.println(orders.getProduct().getManufacturor());
            order.getOrderItems().add(orders);
        }
        // orderItemsRepository.save(orderItems);
        System.out.println(order.getCustomer().getName());
        // System.out.println();
        ApiSuccessResponse apiSuccessResponse = ApiSuccessResponse.builder().timeStamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value()).successmessage("Orrder created").data(order).build();
        
        // apiSuccessResponse.setTimeStamp(LocalDateTime.now());
        // apiSuccessResponse.setSuccessmessage("Order Created");
        // apiSuccessResponse.setStatus(HttpStatus.CREATED.value());
        // apiSuccessResponse.setData(order);

        return apiSuccessResponse;

    }
}
