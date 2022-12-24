package com.api.AddressApi.service;

import com.api.AddressApi.model.Customer;
import com.api.AddressApi.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Optional<Customer> findById(UUID idCustomer) {
        return repository.findById(idCustomer);
    }

    @Transactional
    public void delete(Customer customer) {
        repository.delete(customer);
    }
}
