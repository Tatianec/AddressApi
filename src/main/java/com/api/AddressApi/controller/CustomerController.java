package com.api.AddressApi.controller;

import com.api.AddressApi.model.Customer;
import com.api.AddressApi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @GetMapping
    public List<Customer> findAll(){
        List<Customer> result = repository.findAll();

        return result;
    }

    @GetMapping(value = "/{id_customer}")
    public Customer findById(@PathVariable int id_customer){
        Customer result = repository.findById(id_customer).get();

        return result;
    }

    @PostMapping()
    public Customer insert(@RequestBody Customer customer){
        Customer result = repository.save(customer);

        return result;
    }
}
