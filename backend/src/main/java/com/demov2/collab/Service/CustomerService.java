package com.demov2.collab.Service;

import com.demov2.collab.Entity.Customer;
import com.demov2.collab.Entity.Enums.CustomerStatus;
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
        customer.setCustomerStatus(CustomerStatus.Active);
        customerRepository.save(customer);
        return  "Customer added Successfully";
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
