package com.demov2.collab.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequestMapping ("/api/v1/order")
public class OrderController {
    
    @GetMapping("{id}")
    public String getMethodName(@PathVariable Long id) {
        return new String();
    }
    
}
