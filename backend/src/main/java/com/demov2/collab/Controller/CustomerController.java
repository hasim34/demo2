package com.demov2.collab.Controller;


import com.demov2.collab.DTO.CreateCustomerRequest;
import com.demov2.collab.Entity.Customer;
import com.demov2.collab.Service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
;
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }
    @PostMapping("/customer")
    public ResponseEntity<?> addCustomer (CreateCustomerRequest createCustomerRequest){
        return  ResponseEntity.status(HttpStatus.CREATED).body( customerService.addCustomer(createCustomerRequest));
    }




}
