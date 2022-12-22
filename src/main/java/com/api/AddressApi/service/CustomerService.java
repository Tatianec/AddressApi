package com.api.AddressApi.service;

import com.api.AddressApi.model.Customer;
import com.api.AddressApi.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Customer save(Customer customerModel) {
        return repository.save(customerModel);
    }
}
