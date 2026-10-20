package com.demov2.collab.Service;

import com.demov2.collab.Controller.CustomerController;
import com.demov2.collab.DTO.CreateCustomerRequest;
import com.demov2.collab.Entity.Customer;
import com.demov2.collab.Entity.Enums.CustomerStatus;
import com.demov2.collab.Repository.CustomerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    // private final CustomerController customerController;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
        // this.customerController = customerController;
    }


    public ResponseEntity<Customer> addCustomer(CreateCustomerRequest createCustomerRequest){
        Customer customer = new Customer();
        System.out.println(createCustomerRequest.getName());
        customer.setAddress(createCustomerRequest.getAddress());
        customer.setName(createCustomerRequest.getName());
        customer.setPhoneNumber(createCustomerRequest.getPhoneNumber());
        customer.setCustomerStatus(CustomerStatus.Active);
        customerRepository.save(customer);
        System.out.println(customer.getName());
        return new ResponseEntity<>(customer,HttpStatus.CREATED);
    }

    public String addCustomers(List<Customer> customers){
        customerRepository.saveAll(customers);
        return  "Customers added Successfully";
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public Customer getCustomer(Long id){
        return  customerRepository.findById(id).orElse(null);
    }


    public String  updateStatus(Long id , CustomerStatus customerStatus){
     Customer customer = customerRepository.findById(id).orElseThrow(()->new RuntimeException("user Not Found"));

     customer.setCustomerStatus(customerStatus);
     customerRepository.save(customer);
     return "Customer status updated Successfully";

    }

    public String updateCustomer(Long id, Customer customer){
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(()->new RuntimeException("user Not Found"));

        existingCustomer.setName(customer.getName());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setEmailId(customer.getEmailId());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());

      customerRepository.save(existingCustomer);
      return "Customer Updated Successfully";
    }


}
