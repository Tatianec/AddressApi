package com.api.AddressApi.service;

import com.api.AddressApi.Dto.CustomerDto;
import com.api.AddressApi.exception.ResourceNotFoundException;
import com.api.AddressApi.model.Customer;
import com.api.AddressApi.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final ModelMapper mapper;
    @Transactional
    public Customer save(CustomerDto customerDto) {
        return repository.save(mapper.map(customerDto, Customer.class));
    }
    @Transactional
    public Customer update(CustomerDto customerDto, Long id) {
        Customer customer = findById(id);
        mapper.map(customerDto, customer);
        repository.save(customer);
        return customer;
    }
    @Transactional
    public void delete(Long id) {
        Customer customer = mapper.map(this.findById(id), Customer.class);
        repository.delete(customer);
    }
    public Page<Customer> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Customer findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found!"));
    }
}
