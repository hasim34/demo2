package com.demov2.collab.Service;

import com.demov2.collab.Entity.Customer;
import com.demov2.collab.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;


    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }


    public String addCustomer(Customer customer){
        customerRepository.save(customer);
        return  "Customer add Successfully";
    }

    public String addCustomers(List<Customer> customers){
        customerRepository.saveAll(customers);
        return  "Customers add Successfully";
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public Customer getCustomer(Long id){
        return  customerRepository.findById(id).orElse(null);
    }



}
