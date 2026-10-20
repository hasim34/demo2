package com.demov2.collab.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.demov2.collab.DTO.ApiErrorResponse;
import com.demov2.collab.DTO.OrderDto;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController 
@RequestMapping ("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    
    // private final OrderService
    @GetMapping("{id}")
    public String getMethodName(@PathVariable Long id) {
        return new String();
    }

    @PostMapping
    public ResponseEntity<ApiErrorResponse> postMethodName(@RequestBody OrderDto order) {
        //TODO: process POST request
        
        
        return null;
    }
    
    
}
